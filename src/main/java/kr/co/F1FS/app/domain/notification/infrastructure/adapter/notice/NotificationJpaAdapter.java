package kr.co.F1FS.app.domain.notification.infrastructure.adapter.notice;

import kr.co.F1FS.app.domain.notification.application.port.out.notice.NotificationJpaPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.NotificationRepository;
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

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification saveAndFlush(Notification notification) {
        return notificationRepository.saveAndFlush(notification);
    }

    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
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
    public Page<Notification> findAllByAuthor(String author, Pageable pageable) {
        return notificationRepository.findAllByAuthor(author, pageable);
    }

    @Override
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }
}
