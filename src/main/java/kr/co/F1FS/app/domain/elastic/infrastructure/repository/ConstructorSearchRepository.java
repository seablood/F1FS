package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConstructorSearchRepository extends ElasticsearchRepository<ConstructorDocument, Long> {
}
