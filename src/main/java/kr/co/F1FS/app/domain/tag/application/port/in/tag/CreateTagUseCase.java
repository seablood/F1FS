package kr.co.F1FS.app.domain.tag.application.port.in.tag;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.tag.domain.Tag;

import java.util.List;

public interface CreateTagUseCase {
    void save(Post post, List<Tag> tags);
}
