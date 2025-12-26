package kr.co.F1FS.app.domain.notification.application.port.in.redis;

import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteNotificationRedisUseCase {
    void deleteNotification(User user, Long id, String key);
}
