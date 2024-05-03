package org.techcrumbs.search.management;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.opensearch.OpenSearchStatusException;
import org.opensearch.action.admin.indices.alias.Alias;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.CreateIndexResponse;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.core.rest.RestStatus;
import org.techcrumbs.search.management.exceptions.IndexAlreadyExistsException;
import org.techcrumbs.search.model.Index;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class IndexManager {

    private final RestHighLevelClient highLevelClient;

    public IndexManager(RestHighLevelClient highLevelClient) {
        this.highLevelClient = highLevelClient;
    }

    public void createIndex(Index index) throws Exception {

        CreateIndexRequest request = new CreateIndexRequest(index.name());

        if (index.hasAlias()) {
            request.aliases(index.aliases().stream().map(Alias::new).toList());
        }

        Gson gson = new Gson();
        Map<String, Object> mappings;
        try (InputStream is = IndexManager.class.getResourceAsStream(index.mappingFile())) {
            mappings = gson.fromJson(new InputStreamReader(is),
                    new TypeToken<Map<String, Object>>() {}.getType());
        }

        Map<String, Object> settings = new HashMap<>();
        settings.put("index.number_of_shards", index.numShards());
        settings.put("index.number_of_replicas", index.numReplicas());
        index.refreshIntervalOptional().ifPresent(
                refreshInterval -> settings.put("index.refresh_interval", refreshInterval + "s"));

        Map<String, Object> source = new HashMap<>();
        source.put("settings", settings);
        source.put("mappings", mappings);

        request.source(source);
        try {
            CreateIndexResponse createIndexResponse =
                    highLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (! createIndexResponse.isAcknowledged()) {
                String errorMsg = "Could not create index: " + index.name() + "Response: " + createIndexResponse;
                throw new Exception(errorMsg);
            }
        } catch (OpenSearchStatusException ex) {
            // if the index already exists,
            // we are getting OpenSearchStatusException: [type=resource_already_exists_exception,
            // reason=index [am_alert_message_index/nbCQD9ONTJmYGHXLJbyz_w] already exists]
            if (checkIndexExists(index)) {
                throw new IndexAlreadyExistsException(index);
            }
            throw ex;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkIndexExists(final Index index) throws IOException {
        try {
            return highLevelClient.indices().exists(new GetIndexRequest(index.name()), RequestOptions.DEFAULT);
        } catch (OpenSearchStatusException e) {
            if (RestStatus.NOT_FOUND.getStatus() == e.status().getStatus()) return false;
            throw new RuntimeException(e);
        }
    }

}
