package org.techcrumbs.data.movies.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.opensearch.client.RestHighLevelClient;
import org.techcrumbs.Helper;
import org.techcrumbs.data.movies.Constants;
import org.techcrumbs.data.movies.index.Indices;
import org.techcrumbs.data.movies.index.MovieDataIndexer;
import org.techcrumbs.data.movies.model.Movie;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MoviesDataParser {

    private static final Gson PRETTY_PRINTER_GSON = new GsonBuilder().setPrettyPrinting().create();

    public void parse(String moviesCsvFilePath, Consumer<List<Movie>> batchConsumer, int batchSize) throws IOException {
        // Parsing record wise
        File moviesCsv = new File(moviesCsvFilePath);
        CSVFormat format = CSVFormat.EXCEL.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        CSVParser parser = CSVParser.parse(moviesCsv, Charset.defaultCharset(), format);
        List<String> headers = parser.getHeaderNames();
        List<Movie> batch = new ArrayList<>(batchSize);
        for (CSVRecord record : parser) {
            batch.add(recordToMovieMapper(headers, record));
            if (batchSize == batch.size()) {
                batchConsumer.accept(batch);
                batch = new ArrayList<>();
            }
        }

        if (! batch.isEmpty()) batchConsumer.accept(batch);
    }

    private Movie recordToMovieMapper(List<String> headers, CSVRecord movieRecord) {
        Movie.MovieBuilder builder = new Movie.MovieBuilder();
        for (String header : headers) {
            String value = movieRecord.get(header);
            switch (header) {
                case "adult":
                    builder.setAdult(parseBoolean(value));
                    break;
                case "belongs_to_collection":
                    builder.setMovieCollection(parseMovieCollection(value));
                    break;
                case "budget":
                    builder.setBudget(Long.parseLong(value));
                    break;
                case "genres":
                    builder.setGenres(parseGenres(value));
                    break;
                case "homepage":
                    if (! Helper.isEmptyString(value)) builder.setHomepage(value);
                    break;
                case "id":
                    builder.setId(value);
                    break;
                case "imdb_id":
                    builder.setImdbId(value);
                    break;
                case "original_language":
                    builder.setOriginalLanguage(value);
                    break;
                case "original_title":
                    builder.setOriginalTitle(value);
                    break;
                case "overview":
                    builder.setOverview(value);
                    break;
                case "popularity":
                    builder.setPopularity(Float.parseFloat(value));
                    break;
                case "poster_path":
                    builder.setPosterPath(value);
                    break;
                case "production_companies":
                    builder.setProductionCompanies(parseProductionCompanies(value));
                    break;
                case "production_countries":
                    builder.setProductionCountries(parseProductionCountries(value));
                    break;
                case "release_date":
                    builder.setReleaseDate(value);
                    break;
                case "revenue":
                    builder.setRevenue(Long.parseLong(value));
                    break;
                case "runtime":
                    builder.setRuntime(Double.parseDouble(value));
                    break;
                case "spoken_languages":
                    builder.setSpokenLanguages(parseSpokenLanguages(value));
                    break;
                case "status":
                    builder.setStatus(value);
                    break;
                case "tagline":
                    builder.setTagline(value);
                    break;
                case "title":
                    builder.setTitle(value);
                    break;
                case "video":
                    builder.setVideo(parseBoolean(value));
                    break;
                case "vote_average":
                    builder.setVoteAverage(Double.parseDouble(value));
                    break;
                case "vote_count":
                    builder.setVoteCount(Long.parseLong(value));
                    break;
                default:
                    throw new RuntimeException("Unknown column: " + header);
            }
        }

        Movie movie = builder.createMovie();
        System.out.println(PRETTY_PRINTER_GSON.toJson(movie));
        System.out.println("----------------------------");
        return movie;
    }

    private List<String> parseGenres(String value) {
        List<Map<String, Object>> valueList = toListOfMaps(value);
        return valueList.stream().map(map -> (String) map.get("name")).collect(Collectors.toList());
    }

    private List<String> parseProductionCompanies(String value) {
        List<Map<String, Object>> valueList = toListOfMaps(value);
        return valueList.stream().map(map -> (String) map.get("name")).collect(Collectors.toList());
    }

    private List<String> parseProductionCountries(String value) {
        List<Map<String, Object>> valueList = toListOfMaps(value);
        return valueList.stream().map(map -> (String) map.get("iso_3166_1")).collect(Collectors.toList());
    }

    private List<String> parseSpokenLanguages(String value) {
        List<Map<String, Object>> valueList = toListOfMaps(value);
        return valueList.stream().map(map -> (String) map.get("iso_639_1")).collect(Collectors.toList());
    }

    private Movie.MovieCollection parseMovieCollection(String value) {
        if (Helper.isEmptyString(value.strip())) return null;
        String jsonString = value.replaceAll("'", "\"");
        Map<String, String> map = PRETTY_PRINTER_GSON.fromJson(jsonString, new TypeToken<Map<String, String>>() {}.getType());
        return new Movie.MovieCollection(
                map.get("id"),
                map.get("name"),
                map.get("poster_path"),
                map.get("backdrop_path")
        );
    }


    private boolean parseBoolean(String value) {
        return ! "False".equalsIgnoreCase(value);
    }

    private static List<Map<String, Object>> toListOfMaps(String value) {
        String jsonString = value.replaceAll("'", "\"");
        return PRETTY_PRINTER_GSON.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {}.getType());
    }

    public static void parseAndIndexMovies(RestHighLevelClient highLevelClient) throws Exception {

        // create indices
        new Indices().setup(highLevelClient);

        MovieDataIndexer indexer = new MovieDataIndexer(highLevelClient);
        new MoviesDataParser().parse(
                Constants.moviesMetadataFilepath(),
                indexer::indexMovies,
                Constants.INDEXING_BATCH_SIZE);
    }
}
