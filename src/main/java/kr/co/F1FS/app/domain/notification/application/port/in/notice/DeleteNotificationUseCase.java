package kr.co.F1FS.app.domain.notification.application.port.in.notice;

import kr.co.F1FS.app.domain.notification.domain.Notification;

public interface DeleteNotificationUseCase {
    void delete(Notification notification);
}
