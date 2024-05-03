package org.techcrumbs.data.movies;

public class Constants {

    public static final String MOVIES_DATA_BASE_DIR = "data/movies";

    public static final String MOVIES_METADATA_FILENAME = "movies_metadata.csv";

    public static final int INDEXING_BATCH_SIZE = 50;

    public static String moviesMetadataFilepath() {
        return Constants.MOVIES_DATA_BASE_DIR + "/" + Constants.MOVIES_METADATA_FILENAME;
    }

}
