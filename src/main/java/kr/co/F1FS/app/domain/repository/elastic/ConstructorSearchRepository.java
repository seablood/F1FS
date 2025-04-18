package kr.co.F1FS.app.domain.repository.elastic;

import kr.co.F1FS.app.domain.model.elastic.ConstructorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConstructorSearchRepository extends ElasticsearchRepository<ConstructorDocument, Long> {
}
