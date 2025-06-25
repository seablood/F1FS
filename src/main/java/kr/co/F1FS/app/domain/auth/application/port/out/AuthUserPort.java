package kr.co.F1FS.app.domain.auth.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.Role;

public interface AuthUserPort {
    void save(User user);
    void saveAndFlush(User user);
    User findUserByEmail(String email);
    void delete(User user);
    User findByEmailAndPassword(String email, String password);
}
