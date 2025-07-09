package kr.co.F1FS.app.domain.admin.notification.application.port.out;

import kr.co.F1FS.app.domain.notification.domain.Notification;

import java.util.Optional;

public interface AdminNotificationPort {
    Notification findById(Long id);
    void saveAndFlush(Notification notification);
    void delete(Notification notification);
}
