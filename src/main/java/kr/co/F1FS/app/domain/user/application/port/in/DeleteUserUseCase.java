package kr.co.F1FS.app.domain.user.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteUserUseCase {
    void delete(User user);
}
