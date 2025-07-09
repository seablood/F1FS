package kr.co.F1FS.app.domain.admin.notification.application.service;

import kr.co.F1FS.app.domain.admin.notification.application.port.in.AdminNotificationUseCase;
import kr.co.F1FS.app.domain.admin.notification.application.port.out.AdminNotificationPort;
import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationUseCase;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.domain.admin.notification.presentation.dto.ModifyNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminNotificationService implements AdminNotificationUseCase {
    private final AdminNotificationPort notificationPort;
    private final NotificationUseCase notificationUseCase;
    private final NotificationMapper notificationMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Transactional
    public ResponseNotificationDTO modify(Long id, ModifyNotificationDTO dto){
        Notification notification = notificationPort.findById(id);
        cacheEvictUtil.evictCachingNotification(notification);

        notificationUseCase.modify(notification, dto);
        notificationPort.saveAndFlush(notification);

        return notificationMapper.toResponseNotificationDTO(notification);
    }

    @Transactional
    public void delete(Long id){
        Notification notification = notificationPort.findById(id);
        cacheEvictUtil.evictCachingNotification(notification);

        notificationPort.delete(notification);
    }
}
