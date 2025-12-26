package kr.co.F1FS.app.domain.post.application.port.in.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.ModifyPostDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface UpdatePostUseCase {
    void modify(ModifyPostDTO dto, Post post, User user);
    void increaseLike(Post post);
    void decreaseLike(Post post);
}
