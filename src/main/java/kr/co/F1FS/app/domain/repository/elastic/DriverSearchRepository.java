package kr.co.F1FS.app.domain.repository.elastic;

import kr.co.F1FS.app.domain.model.elastic.DriverDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DriverSearchRepository extends ElasticsearchRepository<DriverDocument, Long> {
    List<DriverDocument> findByKorNameContainingOrEngNameContaining(String korName, String engName);
}
