package org.techcrumbs.search.index;

import org.opensearch.action.DocWriteRequest;
import org.opensearch.action.bulk.BulkItemResponse;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.techcrumbs.Helper;
import org.techcrumbs.search.model.Index;

import java.util.Map;

public class IndexWriter {

    private RestHighLevelClient highLevelClient;

    public IndexWriter(RestHighLevelClient highLevelClient) {
        this.highLevelClient = highLevelClient;
    }

    public void addDocuments(final Index index, final Map<String, String> docs) throws Exception {

        if (Helper.isEmptyMap(docs)) {
            return;
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (Map.Entry<String, String> docEntry : docs.entrySet()) {
            String docId = docEntry.getKey();
            String docJson = docEntry.getValue();

            IndexRequest indexRequest = new IndexRequest(index.referenceName())
                    .id(docId)
                    .source(docJson, XContentType.JSON)
                    .opType(DocWriteRequest.OpType.INDEX);

            bulkRequest.add(indexRequest);
        }

        final BulkResponse bulkResponse = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        logAndThrowError(bulkResponse);
    }

    static void logAndThrowError(final BulkResponse bulkResponse) {
        boolean throwError = false;
        if (bulkResponse.hasFailures()) {
            BulkItemResponse[] responseItems = bulkResponse.getItems();
            StringBuilder builder = new StringBuilder("Exception Occurred->\n");
            int i = 1;
            for (BulkItemResponse bulkItemResponse : responseItems) {
                BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                if (failure == null) {
                    continue;
                }
                throwError = true;
                builder.append("Error-");
                builder.append(i++);
                builder.append(":");
                builder.append(bulkItemResponse.getFailureMessage());
                builder.append("\n");
            }

            if (throwError) {
                System.out.println(builder.toString());
                throw new RuntimeException("ES bulk operation failed");
            }
        }
    }

}
