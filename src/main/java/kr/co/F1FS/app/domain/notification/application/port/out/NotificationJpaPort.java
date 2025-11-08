package kr.co.F1FS.app.domain.notification.application.port.out;

import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationJpaPort {
    Notification save(Notification notification);
    Notification saveAndFlush(Notification notification);
    Page<SimpleResponseNotificationDTO> findAll(Pageable pageable);
    Notification findById(Long id);
    Notification findByRedisId(Long redisId);
}
