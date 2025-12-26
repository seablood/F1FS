package kr.co.F1FS.app.domain.notification.application.port.in.notice;

import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryNotificationUseCase {
    Notification findById(Long id);
    ResponseNotificationDTO findByIdForDTO(Long id);
    ResponseNotificationDTO findByRedisIdForDTO(Long redisId);
    Page<SimpleResponseNotificationDTO> findAll(Pageable pageable);
}
