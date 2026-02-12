package kr.co.F1FS.app.domain.user.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserJpaPort {
    User save(User user);
    User saveAndFlush(User user);
    User findById(Long id);
    Page<User> findAll(Pageable pageable);
    User findByUsername(String username);
    Optional<User> findByUsernameForOptional(String username);
    User findByNickname(String nickname);
    User findUserByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    User findByUsernameOrNull(String username);
    User findByEmailAndPassword(String email, String password);
    User findByProviderAndProviderIdOrNull(String provider, String ProviderId);
    List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo);
    void delete(User user);
}
