package com.zkdlu.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig {
    @Bean
    public RestHighLevelClient client() {
        RestClientBuilder builder = RestClient.builder(
                HttpHost.create("http://localhost:9200")
        );

        return new RestHighLevelClient(builder);
    }
}
