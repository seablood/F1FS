package kr.co.F1FS.app.domain.notification.application.service.notice;

import kr.co.F1FS.app.domain.notification.application.mapper.notice.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.QueryNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.notice.NotificationJpaPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryNotificationService implements QueryNotificationUseCase {
    private final NotificationJpaPort notificationJpaPort;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification findById(Long id) {
        return notificationJpaPort.findById(id);
    }

    @Override
    public ResponseNotificationDTO findByIdForDTO(Long id) {
        return notificationMapper.toResponseNotificationDTO(notificationJpaPort.findById(id));
    }

    @Override
    public ResponseNotificationDTO findByRedisIdForDTO(Long redisId) {
        return notificationMapper.toResponseNotificationDTO(notificationJpaPort.findByRedisId(redisId));
    }

    @Override
    public Page<SimpleResponseNotificationDTO> findAll(Pageable pageable) {
        return notificationJpaPort.findAll(pageable).map(notification -> notificationMapper.toSimpleResponseNotificationDTO(
                notification
        ));
    }
}
