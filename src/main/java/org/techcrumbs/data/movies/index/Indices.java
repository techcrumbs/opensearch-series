package org.techcrumbs.data.movies.index;

import org.opensearch.client.RestHighLevelClient;
import org.techcrumbs.search.management.IndexManager;
import org.techcrumbs.search.model.Index;

import java.util.List;
import java.util.Optional;

public class Indices {

    private static final String MOVIES_METADATA_INDEX_SCHEMA = "/search/schema/movies_metadata_schema.json";

    public static final Index MOVIES_METADATA_INDEX = new Index(
            "tc_v1_movies_metadata-00001",
            // List.of("movies_metadata"),
            List.of(),
            3,
            1,
            MOVIES_METADATA_INDEX_SCHEMA,
            Optional.empty()
    );

    public void setup(RestHighLevelClient highLevelClient) throws Exception {

        // create movies_metadata index
        new IndexManager(highLevelClient).createIndex(MOVIES_METADATA_INDEX);
        System.out.println("######## MOVIES_METADATA_INDEX CREATED ########");
    }
}
