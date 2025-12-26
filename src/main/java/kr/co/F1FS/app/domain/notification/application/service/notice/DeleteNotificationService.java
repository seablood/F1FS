package kr.co.F1FS.app.domain.notification.application.service.notice;

import kr.co.F1FS.app.domain.notification.application.port.in.notice.DeleteNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.notice.NotificationJpaPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteNotificationService implements DeleteNotificationUseCase {
    private final NotificationJpaPort notificationJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(Notification notification) {
        cacheEvictUtil.evictCachingNotification(notification);

        notificationJpaPort.delete(notification);
    }
}
