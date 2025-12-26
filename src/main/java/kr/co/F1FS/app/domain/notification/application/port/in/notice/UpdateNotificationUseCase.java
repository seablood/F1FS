package kr.co.F1FS.app.domain.notification.application.port.in.notice;

import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;

public interface UpdateNotificationUseCase {
    ResponseNotificationDTO modify(Notification notification, ModifyNotificationDTO dto);
}
