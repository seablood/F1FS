package kr.co.F1FS.app.domain.user.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserJpaPort {
    User save(User user);
    User saveAndFlush(User user);
    void saveAllAndFlush(List<User> list);
    User findByNickname(String nickname);
    User findUserByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    User findByUsernameOrNull(String username);
    User findByEmailAndPassword(String email, String password);
    List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo);
    void delete(User user);
}
