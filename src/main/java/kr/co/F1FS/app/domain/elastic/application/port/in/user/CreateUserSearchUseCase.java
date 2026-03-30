package kr.co.F1FS.app.domain.elastic.application.port.in.user;

import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateUserSearchUseCase {
    void save(User user);
}
