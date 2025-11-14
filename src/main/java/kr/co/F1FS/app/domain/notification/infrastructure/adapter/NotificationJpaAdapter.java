package kr.co.F1FS.app.domain.notification.infrastructure.adapter;

import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.out.NotificationJpaPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.NotificationRepository;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import kr.co.F1FS.app.global.util.exception.notification.NotificationException;
import kr.co.F1FS.app.global.util.exception.notification.NotificationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationJpaAdapter implements NotificationJpaPort {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification saveAndFlush(Notification notification) {
        return notificationRepository.saveAndFlush(notification);
    }

    @Override
    public Page<SimpleResponseNotificationDTO> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(notification -> notificationMapper.toSimpleResponseNotificationDTO(
                notification
        ));
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationException(NotificationExceptionType.NOTIFICATION_NOT_FOUND));
    }

    @Override
    public Notification findByRedisId(Long redisId) {
        return notificationRepository.findByRedisId(redisId)
                .orElseThrow(() -> new NotificationException(NotificationExceptionType.NOTIFICATION_NOT_FOUND));
    }

    @Override
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }
}
