package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface PostComplainUseCase {
    void save(PostComplain complain);
    void postComplain(Long id, User user, CreatePostComplainDTO dto);
}
