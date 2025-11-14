package kr.co.F1FS.app.domain.notification.application.port.in;

import kr.co.F1FS.app.domain.admin.notification.presentation.dto.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import org.springframework.data.domain.Page;

public interface NotificationUseCase {
    void saveNotification(NotificationRedis redis, String content);
    Notification saveAndFlush(Notification notification);
    Notification findByIdNotDTONotCache(Long id);
    ResponseNotificationDTO getNotificationByRedisId(Long redisId);
    ResponseNotificationDTO getNotificationById(Long id);
    Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size);
    void modify(Notification notification, ModifyNotificationDTO dto);
    void delete(Notification notification);
    ResponseNotificationDTO toResponseNotificationDTO(Notification notification);
}
