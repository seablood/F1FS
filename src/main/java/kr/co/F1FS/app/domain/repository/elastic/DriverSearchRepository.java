package kr.co.F1FS.app.domain.repository.elastic;

import kr.co.F1FS.app.domain.model.elastic.DriverDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DriverSearchRepository extends ElasticsearchRepository<DriverDocument, Long> {

}
