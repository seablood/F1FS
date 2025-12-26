package kr.co.F1FS.app.domain.notification.application.port.in.admin;

import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;

public interface AdminNotificationUseCase {
    void addPushDTO(FCMPushDTO pushDTO);
    void sendPushForLiveInfo(FCMPushDTO pushDTO);
    ResponseNotificationDTO modify(Long id, ModifyNotificationDTO dto);
    void delete(Long id);
}
