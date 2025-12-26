package kr.co.F1FS.app.domain.notification.application.port.in.notice;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;

public interface CreateNotificationUseCase {
    void save(NotificationRedis redis, String content);
}
