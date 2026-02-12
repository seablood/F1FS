package kr.co.F1FS.app.domain.tag.application.port.in.relation;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.tag.domain.Tag;

public interface CreateTagPostRelationUseCase {
    void save(Post post, Tag tag);
}
