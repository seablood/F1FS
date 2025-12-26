package kr.co.F1FS.app.domain.notification.application.service.notice;

import kr.co.F1FS.app.domain.notification.application.port.in.notice.CreateNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.notice.NotificationJpaPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNotificationService implements CreateNotificationUseCase {
    private final NotificationJpaPort notificationJpaPort;
    private final NotificationDomainService notificationDomainService;

    @Override
    public void save(NotificationRedis redis, String content) {
        Notification notification = notificationDomainService.createEntity(redis, content);

        notificationJpaPort.save(notification);
    }
}
