package kr.co.F1FS.app.domain.notification.infrastructure.repository;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
    Optional<FCMToken> findByUserId(Long userId);
}
