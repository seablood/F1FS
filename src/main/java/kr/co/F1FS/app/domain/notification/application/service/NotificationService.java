package kr.co.F1FS.app.domain.notification.application.service;

import kr.co.F1FS.app.domain.admin.notification.presentation.dto.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationUseCase;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.NotificationRepository;
import kr.co.F1FS.app.global.util.exception.notification.NotificationException;
import kr.co.F1FS.app.global.util.exception.notification.NotificationExceptionType;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationUseCase {
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveNotification(NotificationRedis redis, String content){
        Notification notification = notificationMapper.toNotification(redis, content);

        notificationRepository.save(notification);
    }

    @Cacheable(value = "NotificationDTOByRedisId", key = "#redisId", cacheManager = "redisLongCacheManager")
    public ResponseNotificationDTO getNotificationByRedisId(Long redisId){
        Notification notification = notificationRepository.findByRedisId(redisId)
                .orElseThrow(() -> new NotificationException(NotificationExceptionType.NOTIFICATION_NOT_FOUND));
        return notificationMapper.toResponseNotificationDTO(notification);
    }

    public ResponseNotificationDTO getNotificationById(Long id){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationException(NotificationExceptionType.NOTIFICATION_NOT_FOUND));
        return notificationMapper.toResponseNotificationDTO(notification);
    }

    public Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return notificationRepository.findAll(pageable).map(notification -> notificationMapper.toSimpleResponseNotificationDTO(
                notification
        ));
    }

    public void modify(Notification notification, ModifyNotificationDTO dto){
        notification.modify(dto);
    }
}
