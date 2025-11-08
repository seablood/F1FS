package kr.co.F1FS.app.domain.elastic.application.port.in;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDocumentDTO;
import org.springframework.data.domain.Page;

public interface PostSearchUseCase {
    void save(Post post);
    void save(PostDocument postDocument);
    PostDocument findById(Long id);
    void modify(PostDocument document, Post post);
    void delete(PostDocument document);
    void increaseLikeNum(PostDocument postDocument);
    void decreaseLikeNum(PostDocument postDocument);
    Page<ResponsePostDocumentDTO> getPostList(int page, int size, String condition, String option, String keyword);
}
