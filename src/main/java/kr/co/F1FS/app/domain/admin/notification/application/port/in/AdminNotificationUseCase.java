package kr.co.F1FS.app.domain.admin.notification.application.port.in;

import kr.co.F1FS.app.domain.admin.notification.presentation.dto.ModifyNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;

public interface AdminNotificationUseCase {
    ResponseNotificationDTO modify(Long id, ModifyNotificationDTO dto);
    void delete(Long id);
}
