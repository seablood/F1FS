package kr.co.F1FS.app.domain.repository.elastic;

import kr.co.F1FS.app.domain.model.elastic.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, Long> {
}
