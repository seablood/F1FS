package kr.co.F1FS.app.domain.notification.application.service.notice;

import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.application.mapper.notice.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.UpdateNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.notice.NotificationJpaPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateNotificationService implements UpdateNotificationUseCase {
    private final NotificationJpaPort notificationJpaPort;
    private final NotificationDomainService notificationDomainService;
    private final NotificationMapper notificationMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public ResponseNotificationDTO modify(Notification notification, ModifyNotificationDTO dto) {
        cacheEvictUtil.evictCachingNotification(notification);

        notificationDomainService.modify(notification, dto);
        notificationJpaPort.saveAndFlush(notification);

        return notificationMapper.toResponseNotificationDTO(notification);
    }
}
