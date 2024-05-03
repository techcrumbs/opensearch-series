package org.techcrumbs.search;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;

public class ClientProvider {


    public static RestHighLevelClient newHighLevelRestClient() {
        //Point to keystore with appropriate certificates for security.
        System.setProperty("javax.net.ssl.trustStore", System.getenv("JAVA_CUSTOM_TRUSTSTORE_PATH"));
        System.setProperty("javax.net.ssl.trustStorePassword", System.getenv("JAVA_CUSTOM_TRUSTSTORE_PASSWORD"));

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        String userName = System.getenv("OPENSEARCH_ADMIN_USER");
        String password = System.getenv("OPENSEARCH_ADMIN_PASSWORD");

        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(userName, password));

        //Create a client.
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "https"))
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }
}
