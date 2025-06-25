package kr.co.F1FS.app.domain.notification.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.notification.application.port.out.AdminNotificationPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.NotificationRepository;
import kr.co.F1FS.app.global.util.exception.notification.NotificationException;
import kr.co.F1FS.app.global.util.exception.notification.NotificationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminNotificationAdapter implements AdminNotificationPort {
    private final NotificationRepository notificationRepository;

    @Override
    @Cacheable(value = "Notification", key = "#id", cacheManager = "redisLongCacheManager")
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationException(NotificationExceptionType.NOTIFICATION_NOT_FOUND));
    }

    @Override
    public void saveAndFlush(Notification notification) {
        notificationRepository.saveAndFlush(notification);
    }

    @Override
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }
}
