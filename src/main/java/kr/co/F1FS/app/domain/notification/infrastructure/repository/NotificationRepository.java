package kr.co.F1FS.app.domain.notification.infrastructure.repository;

import kr.co.F1FS.app.domain.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByRedisId(Long redisId);
    Page<Notification> findAll(Pageable pageable);
}
