package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GrandPrixSearchRepository extends ElasticsearchRepository<GrandPrixDocument, Long> {
}
