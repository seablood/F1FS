package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostRoomSearchRepository extends ElasticsearchRepository<PostRoomDocument, Long> {
}
