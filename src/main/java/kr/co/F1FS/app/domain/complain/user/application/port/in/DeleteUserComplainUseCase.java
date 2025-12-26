package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteUserComplainUseCase {
    void delete(UserComplain userComplain, User user);
    void delete(UserComplain userComplain);
}
