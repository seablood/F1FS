package kr.co.F1FS.app.domain.elastic.application.port.in.post;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.domain.Post;

import java.util.List;

public interface UpdatePostSearchUseCase {
    void modify(PostDocument document, Post post, List<String> tags);
    void increaseLikeNum(PostDocument document);
    void decreaseLikeNum(PostDocument document);
}
