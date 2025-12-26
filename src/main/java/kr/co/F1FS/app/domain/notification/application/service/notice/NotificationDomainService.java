package kr.co.F1FS.app.domain.notification.application.service.notice;

import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.application.mapper.notice.NotificationMapper;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationDomainService {
    private final NotificationMapper notificationMapper;

    public Notification createEntity(NotificationRedis redis, String content){
        return notificationMapper.toNotification(redis, content);
    }

    public void modify(Notification notification, ModifyNotificationDTO dto){
        notification.modify(dto);
    }
}
