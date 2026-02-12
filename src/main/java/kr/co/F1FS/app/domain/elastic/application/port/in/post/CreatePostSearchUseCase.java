package kr.co.F1FS.app.domain.elastic.application.port.in.post;

import kr.co.F1FS.app.domain.post.domain.Post;

import java.util.List;

public interface CreatePostSearchUseCase {
    void save(Post post, List<String> tags);
}
