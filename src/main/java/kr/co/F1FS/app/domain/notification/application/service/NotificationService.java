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

    @Override
    @Transactional
    public void saveNotification(NotificationRedis redis, String content){
        Notification notification = notificationMapper.toNotification(redis, content);

        notificationJpaPort.save(notification);
    }

    @Override
    public Notification saveAndFlush(Notification notification) {
        return notificationJpaPort.saveAndFlush(notification);
    }

    @Override
    public Notification findByIdNotDTONotCache(Long id) {
        return notificationJpaPort.findById(id);
    }

    @Override
    @Cacheable(value = "NotificationDTOByRedisId", key = "#redisId", cacheManager = "redisLongCacheManager")
    public ResponseNotificationDTO getNotificationByRedisId(Long redisId){
        Notification notification = notificationJpaPort.findByRedisId(redisId);
        return notificationMapper.toResponseNotificationDTO(notification);
    }

    @Override
    public ResponseNotificationDTO getNotificationById(Long id){
        Notification notification = notificationJpaPort.findById(id);
        return notificationMapper.toResponseNotificationDTO(notification);
    }

    @Override
    public Page<SimpleResponseNotificationDTO> getNotificationList(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return notificationJpaPort.findAll(pageable);
    }

    @Override
    public void modify(Notification notification, ModifyNotificationDTO dto){
        notification.modify(dto);
    }

    @Override
    public void delete(Notification notification) {
        notificationJpaPort.delete(notification);
    }

    @Override
    public ResponseNotificationDTO toResponseNotificationDTO(Notification notification) {
        return notificationMapper.toResponseNotificationDTO(notification);
    }
}
