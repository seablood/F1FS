package kr.co.F1FS.app.domain.repository.rdb.fcm;

import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FCMNotificationRepository extends JpaRepository<FCMNotification, Long> {
    Optional<FCMNotification> findByUserId(Long userId);
}
