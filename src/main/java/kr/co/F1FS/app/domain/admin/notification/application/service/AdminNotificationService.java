package kr.co.F1FS.app.domain.admin.notification.application.service;

import kr.co.F1FS.app.domain.admin.notification.application.port.in.AdminNotificationUseCase;
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
    private final NotificationUseCase notificationUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public ResponseNotificationDTO modify(Long id, ModifyNotificationDTO dto){
        Notification notification = notificationUseCase.findByIdNotDTONotCache(id);
        cacheEvictUtil.evictCachingNotification(notification);

        notificationUseCase.modify(notification, dto);
        notificationUseCase.saveAndFlush(notification);

        return notificationUseCase.toResponseNotificationDTO(notification);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Notification notification = notificationUseCase.findByIdNotDTONotCache(id);
        cacheEvictUtil.evictCachingNotification(notification);

        notificationUseCase.delete(notification);
    }
}
