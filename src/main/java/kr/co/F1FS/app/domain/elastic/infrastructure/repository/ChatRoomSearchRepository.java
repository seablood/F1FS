package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ChatRoomSearchRepository extends ElasticsearchRepository<ChatRoomDocument, Long> {
}
