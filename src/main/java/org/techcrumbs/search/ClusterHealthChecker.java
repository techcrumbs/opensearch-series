package org.techcrumbs.search;

import org.opensearch.action.admin.cluster.health.ClusterHealthRequest;
import org.opensearch.action.admin.cluster.health.ClusterHealthResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.cluster.health.ClusterHealthStatus;

import java.io.IOException;

public class ClusterHealthChecker {

    public static ClusterHealthStatus checkHealth() {
        try (RestHighLevelClient client = ClientProvider.newHighLevelRestClient()) {
            ClusterHealthResponse clusterHealthResponse =
                    client.cluster().health(new ClusterHealthRequest(), RequestOptions.DEFAULT);

            System.out.println("ClusterHealthResponse: " + clusterHealthResponse);
            return clusterHealthResponse.getStatus();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
