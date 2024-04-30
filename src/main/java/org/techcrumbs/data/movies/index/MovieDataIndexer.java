package org.techcrumbs.data.movies.index;

import com.google.gson.Gson;
import org.opensearch.client.RestHighLevelClient;
import org.techcrumbs.data.movies.model.Movie;
import org.techcrumbs.search.index.IndexWriter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieDataIndexer {

    private final RestHighLevelClient highLevelClient;

    public MovieDataIndexer(RestHighLevelClient highLevelClient) {
        this.highLevelClient = highLevelClient;
    }

    public void indexMovies(List<Movie> movies) {

        Gson gson = new Gson();
        Map<String, String> docs = movies.stream().collect(Collectors.toMap(Movie::getId, gson::toJson));
        try {
            new IndexWriter(highLevelClient).addDocuments(Indices.MOVIES_METADATA_INDEX, docs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
