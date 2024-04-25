package org.techcrumbs;

import org.opensearch.action.admin.cluster.health.ClusterHealthRequest;
import org.opensearch.action.admin.cluster.health.ClusterHealthResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (RestHighLevelClient client = ClientProvider.newHighLevelRestClient()) {
            ClusterHealthResponse clusterHealthResponse =
                    client.cluster().health(new ClusterHealthRequest(), RequestOptions.DEFAULT);

            System.out.println("ClusterHealthResponse: " + clusterHealthResponse);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}