package co.empathy.academy.gametracker.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticSearchConfiguration {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String elasticsearchClusternodes;

    @Value("${spring.data.elasticsearch.properties.restClient.username}")
    private String elasticsearchUsername;

    @Value("${spring.data.elasticsearch.properties.restClient.password}")
    private String elasticsearchPassword;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo(elasticsearchClusternodes)
                .usingSsl()
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();

        return RestClients.create(configuration).rest();
    }

}
