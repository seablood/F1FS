package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DriverSearchRepository extends ElasticsearchRepository<DriverDocument, Long> {

}
