package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.TagDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TagSearchRepository extends ElasticsearchRepository<TagDocument, Long> {
}
