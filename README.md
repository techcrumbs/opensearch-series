[Opensearch Series - Episode 1] https://youtu.be/SH370SNoVVk


# Connecting to OpenSearch over HTTPS using the Java High-Level REST Client


### Objectives

1. Run a opensearch single-node instance locally using docker
2. Connect to the instance using Opensearch Java High-Level Rest Client



## Run opensearch


```

# pull opensearch image
docker pull opensearchproject/opensearch:2
 
 # For OpenSearch 2.12 or greater, set a new custom admin password before installation using the following command:
 docker run -d -p 9200:9200 -p 9600:9600 -e "discovery.type=single-node" -e "OPENSEARCH_INITIAL_ADMIN_PASSWORD=$OPENSEARCH_ADMIN_PASSWORD" opensearchproject/opensearch:latest
 
 
 # check opensearch is running
 curl -ku $OPENSEARCH_ADMIN_USER:$OPENSEARCH_ADMIN_PASSWORD https://localhost:9200
 
 # more details on the -k options is cURL https://curl.se/docs/manpage.html#-k
 # It is basically an insecure connection, no certificate validation is done
  
 # check container id
 docker container ls | grep opensearch
 
 # stop the container
 docker stop <containerId>
 
```

<br>

## Connect Opensearch using Opensearch Java HighlevelRestClient

```

# copy certificate from opensearch docker container to local file system
docker cp {container}:{HOME}/config/root-ca.pem ./opensearch-root-ca.pem

# Generate custom keyStore/trustStore
keytool -keystore clientkeystore.jks -genkey -keyalg RSA -alias client

# Add opensearch server certificate to trustStrore
keytool -import -keystore {keystore} -file {file} -alias {alias}


```

