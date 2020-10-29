package com.zkdlu.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateAction;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

@Service
public class IndexService {
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public IndexService(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public CreateIndexResponse createIndex(String index) throws IOException {
        CreateIndexRequest request =  new CreateIndexRequest(index);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));

        CreateIndexResponse response = client.indices()
                .create(request, DEFAULT);

        return response;
    }

    public AcknowledgedResponse deleteIndex(String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);

        AcknowledgedResponse response = client.indices()
                .delete(request, DEFAULT);

        return response;
    }

    public IndexResponse insertDocument(String index, UserDocument user) throws IOException {
        IndexRequest request = new IndexRequest(index).id(user.getId()).source(user);
        IndexResponse response = client.index(request, DEFAULT);

        return response;
    }

    public SearchResponse selectDocument(String index) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);

        SearchRequest request = new SearchRequest(index);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, DEFAULT);

        return response;
    }

    public SearchResponse getQuery(String index, String input) throws IOException {
        QueryStringQueryBuilder searchQeury = QueryBuilders.queryStringQuery(input);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(searchQeury);

        SearchRequest request = new SearchRequest(index);
        request.source(searchSourceBuilder);

        SearchResponse response = client.search(request, DEFAULT);
        return response;
    }
}
