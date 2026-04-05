package kr.co.F1FS.app.domain.elastic.infrastructure.repository;

import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookmarkSearchRepository extends ElasticsearchRepository<BookmarkDocument, Long> {
    List<BookmarkDocument> findAllByPostId(Long postId);
}
