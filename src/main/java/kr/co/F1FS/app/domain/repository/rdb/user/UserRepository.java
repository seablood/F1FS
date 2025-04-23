package kr.co.F1FS.app.domain.repository.rdb.user;

import kr.co.F1FS.app.domain.model.rdb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
    Optional<User> findUserByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime date);
    Optional<User> findByEmailAndPassword(String email, String password);
}
