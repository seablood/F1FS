package kr.co.F1FS.app.domain.notification.application.service;

import kr.co.F1FS.app.domain.admin.notification.presentation.dto.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.NotificationJpaPort;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
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
    private final NotificationJpaPort notificationJpaPort;

    @Transactional
    public void saveNotification(NotificationRedis redis, String content){
        Notification notification = notificationMapper.toNotification(redis, content);

        notificationJpaPort.save(notification);
    }

    @Cacheable(value = "NotificationDTOByRedisId", key = "#redisId", cacheManager = "redisLongCacheManager")
    public ResponseNotificationDTO getNotificationByRedisId(Long redisId){
        Notification notification = notificationJpaPort.findByRedisId(redisId);
        return notificationMapper.toResponseNotificationDTO(notification);
    }

    public ResponseNotificationDTO getNotificationById(Long id){
        Notification notification = notificationJpaPort.findById(id);
        return notificationMapper.toResponseNotificationDTO(notification);
    }

    public Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return notificationJpaPort.findAll(pageable);
    }

    public void modify(Notification notification, ModifyNotificationDTO dto){
        notification.modify(dto);
    }
}
