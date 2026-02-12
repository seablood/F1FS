package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface DeleteUserComplainUseCase {
    void delete(UserComplain userComplain, User user);
    void delete(UserComplain userComplain);
    void delete(List<UserComplain> list);
}
