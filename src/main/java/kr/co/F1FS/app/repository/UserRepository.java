package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
