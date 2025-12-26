package kr.co.F1FS.app.domain.notification.application.service.admin;

import kr.co.F1FS.app.domain.notification.application.port.in.admin.AdminNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.DeleteNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.QueryNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.UpdateNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMGroupUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminNotificationService implements AdminNotificationUseCase {
    private final UpdateNotificationUseCase updateNotificationUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;
    private final QueryNotificationUseCase queryNotificationUseCase;
    private final FCMGroupUseCase fcmGroupUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;

    @Override
    public void addPushDTO(FCMPushDTO pushDTO) {
        fcmGroupUseCase.addPushDTO(pushDTO);
    }

    @Override
    public void sendPushForLiveInfo(FCMPushDTO pushDTO) {
        fcmLiveUseCase.sendPushForLiveInfo(pushDTO);
    }

    @Override
    @Transactional
    public ResponseNotificationDTO modify(Long id, ModifyNotificationDTO dto){
        Notification notification = queryNotificationUseCase.findById(id);

        return updateNotificationUseCase.modify(notification, dto);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Notification notification = queryNotificationUseCase.findById(id);

        deleteNotificationUseCase.delete(notification);
    }
}
