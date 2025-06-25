package kr.co.F1FS.app.domain.notification.application.port.in;

import kr.co.F1FS.app.domain.admin.notification.presentation.dto.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import org.springframework.data.domain.Page;

public interface NotificationUseCase {
    void saveNotification(NotificationRedis redis, String content);
    ResponseNotificationDTO getNotification(Long redisId);
    Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size);
    void modify(Notification notification, ModifyNotificationDTO dto);
}
