package kr.co.F1FS.app.domain.post.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.domain.Post;

public interface PostSearchPort {
    void save(Post post);
    void save(PostDocument document);
    PostDocument findById(Long id);
    void delete(PostDocument document);
}
