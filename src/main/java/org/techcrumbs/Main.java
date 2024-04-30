package org.techcrumbs;


import org.opensearch.client.RestHighLevelClient;
import org.techcrumbs.data.movies.parser.MoviesDataParser;
import org.techcrumbs.search.ClientProvider;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");
        try (RestHighLevelClient highLevelClient = ClientProvider.newHighLevelRestClient()) {
            MoviesDataParser.parseAndIndexMovies(highLevelClient);
        }
    }
}