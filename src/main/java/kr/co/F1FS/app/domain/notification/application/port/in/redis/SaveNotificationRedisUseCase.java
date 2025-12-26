package kr.co.F1FS.app.domain.notification.application.port.in.redis;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;

public interface SaveNotificationRedisUseCase {
    void saveNotification(NotificationRedis redis, String key);
    void saveNotificationForPersonal(NotificationRedis redis, User user);
    void saveNotificationForPersonal(NotificationRedis redis, Long userId);
}
