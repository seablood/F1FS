package kr.co.F1FS.app.domain.post.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;

public interface PostLikeSearchPort {
    void save(PostDocument postDocument);
    PostDocument findById(Long id);
    void increaseLikeNum(PostDocument postDocument);
    void decreaseLikeNum(PostDocument postDocument);
}
