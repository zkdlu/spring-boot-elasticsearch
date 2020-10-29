package com.zkdlu.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        // 클러스터 주소를 제공하기 위해 builder를 사용한다. 디폴트 HttpHeaders나 사용가능한 SSL로 셋한다.
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        // RestHighLevelClient를 만든다.
        return RestClients.create(clientConfiguration).rest();
    }
}
