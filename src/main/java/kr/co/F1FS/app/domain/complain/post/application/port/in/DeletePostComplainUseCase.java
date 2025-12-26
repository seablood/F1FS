package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeletePostComplainUseCase {
    void delete(PostComplain postComplain, User user);
}
