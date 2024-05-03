package org.techcrumbs.data.movies.model;

import java.util.List;

public class Movie {

    public static class MovieCollection {
        private String id;
        private String name;
        private String posterPath;
        private String backdropPath;

        public MovieCollection(String id, String name, String posterPath, String backdropPath) {
            this.id = id;
            this.name = name;
            this.posterPath = posterPath;
            this.backdropPath = backdropPath;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public String getBackdropPath() {
            return backdropPath;
        }
    }

    private boolean adult;

    private MovieCollection movieCollection;

    private long budget;

    private List<String> genres;

    private String homepage;

    private String id;

    private String imdbId;

    private String originalLanguage;

    private String originalTitle;

    private String overview;

    private float popularity;

    private String posterPath;

    private List<String> productionCompanies;

    private List<String> productionCountries;

    private String releaseDate;

    private long revenue;

    private double runtime;

    private List<String> spokenLanguages;

    private String status;

    private String tagline;

    private String title;

    private boolean video;

    private double voteAverage;

    private long voteCount;

    public boolean isAdult() {
        return adult;
    }

    public MovieCollection getMovieCollection() {
        return movieCollection;
    }

    public long getBudget() {
        return budget;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<String> getProductionCompanies() {
        return productionCompanies;
    }

    public List<String> getProductionCountries() {
        return productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public double getRuntime() {
        return runtime;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    private Movie() {
    }

    public static class MovieBuilder {
        private Movie movie = new Movie();

        public MovieBuilder setAdult(boolean adult) {
            movie.adult = adult;
            return this;
        }

        public MovieBuilder setMovieCollection(MovieCollection movieCollection) {
            movie.movieCollection = movieCollection;
            return this;
        }

        public MovieBuilder setBudget(long budget) {
            movie.budget = budget;
            return this;
        }

        public MovieBuilder setGenres(List<String> genres) {
            movie.genres = genres;
            return this;
        }

        public MovieBuilder setHomepage(String homepage) {
            movie.homepage = homepage;
            return this;
        }

        public MovieBuilder setId(String id) {
            movie.id = id;
            return this;
        }

        public MovieBuilder setImdbId(String imdbId) {
            movie.imdbId = imdbId;
            return this;
        }

        public MovieBuilder setOriginalLanguage(String originalLanguage) {
            movie.originalLanguage = originalLanguage;
            return this;
        }

        public MovieBuilder setOriginalTitle(String originalTitle) {
            movie.originalTitle = originalTitle;
            return this;
        }

        public MovieBuilder setOverview(String overview) {
            movie.overview = overview;
            return this;
        }

        public MovieBuilder setPopularity(float popularity) {
            movie.popularity = popularity;
            return this;
        }

        public MovieBuilder setPosterPath(String posterPath) {
            movie.posterPath = posterPath;
            return this;
        }

        public MovieBuilder setProductionCompanies(List<String> productionCompanies) {
            movie.productionCompanies = productionCompanies;
            return this;
        }

        public MovieBuilder setProductionCountries(List<String> productionCountries) {
            movie.productionCountries = productionCountries;
            return this;
        }

        public MovieBuilder setReleaseDate(String releaseDate) {
            movie.releaseDate = releaseDate;
            return this;
        }

        public MovieBuilder setRevenue(long revenue) {
            movie.revenue = revenue;
            return this;
        }

        public MovieBuilder setRuntime(double runtime) {
            movie.runtime = runtime;
            return this;
        }

        public MovieBuilder setSpokenLanguages(List<String> spokenLanguages) {
            movie.spokenLanguages = spokenLanguages;
            return this;
        }

        public MovieBuilder setStatus(String status) {
            movie.status = status;
            return this;
        }

        public MovieBuilder setTagline(String tagline) {
            movie.tagline = tagline;
            return this;
        }

        public MovieBuilder setTitle(String title) {
            movie.title = title;
            return this;
        }

        public MovieBuilder setVideo(boolean video) {
            movie.video = video;
            return this;
        }

        public MovieBuilder setVoteAverage(double voteAverage) {
            movie.voteAverage = voteAverage;
            return this;
        }

        public MovieBuilder setVoteCount(long voteCount) {
            movie.voteCount = voteCount;
            return this;
        }

        public Movie createMovie() {
            return movie;
        }
    }
}
