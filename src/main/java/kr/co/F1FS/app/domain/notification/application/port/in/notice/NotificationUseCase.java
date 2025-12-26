package kr.co.F1FS.app.domain.notification.application.port.in.notice;

import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import org.springframework.data.domain.Page;

public interface NotificationUseCase {
    ResponseNotificationDTO getNotificationByRedisId(Long redisId);
    ResponseNotificationDTO getNotificationById(Long id);
    Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size);
}
