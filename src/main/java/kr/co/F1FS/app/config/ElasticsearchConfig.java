package kr.co.F1FS.app.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "kr.co.F1FS.app.domain.repository.elastic")
public class ElasticsearchConfig {
    @Bean
    public ElasticsearchOperations elasticsearchTemplate(ElasticsearchClient elasticsearchClient){
        return new ElasticsearchTemplate(elasticsearchClient);
    }
}
