package kr.co.F1FS.app.domain.post.application.port.in.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeletePostUseCase {
    void delete(Post post, User user);
    void delete(Post post);
}
