package kr.co.F1FS.app.domain.notification.application.port.in.redis;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface UpdateNotificationRedisUseCase {
    void readNotification(User user, Long id, List<NotificationRedis> list);
}
