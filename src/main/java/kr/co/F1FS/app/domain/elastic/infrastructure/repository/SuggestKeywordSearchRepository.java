package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.SuggestKeywordDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SuggestKeywordSearchRepository extends ElasticsearchRepository<SuggestKeywordDocument, String> {
}
