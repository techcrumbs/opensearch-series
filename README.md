# Episode 1 - Connecting to OpenSearch over HTTPS using the Java High-Level REST Client

[Opensearch Series - Episode 1] https://youtu.be/SH370SNoVVk


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

# once the certificate is added to the trustStore, you can delete the certificate.

```

# Episode 2 - Opensearch Index Overview

- What is an index 
- Use cases
- Run cluster locally
- Index data (index gets auto-created)
- Explain shards (primary / replica)
- Look into index settings / mappings
- Create index explicitly
- Sample Query DSL
- Java code


### Use Cases 
https://www.elastic.co/elasticsearch

### Run distributed cluster locally

https://opensearch.org/docs/latest/install-and-configure/install-opensearch/docker/#deploy-an-opensearch-cluster-using-docker-compose

While running the setup I encounter the following issues:
1. opensearch-nodes exited with error `[1]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`
2. opensearch-nodes exited with OOM

Solve 1.
```
$ colima ssh
$ sudo sysctl -w vm.max_map_count=262144
$ exit
```

<br>

Solve 2.
```
# The default VM created by Colima has 2 CPUs, 2GiB memory and 60GiB storage.
# The VM can be customized either by passing additional flags to colima start. e.g. --cpu, --memory, --disk, --runtime. Or by editing the config file with colima start --edit.

$ colima start --cpu 4 --memory 6

```


