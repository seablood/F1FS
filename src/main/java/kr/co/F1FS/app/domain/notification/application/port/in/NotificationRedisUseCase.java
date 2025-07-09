package kr.co.F1FS.app.domain.notification.application.port.in;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationRedisUseCase {
    void saveNotification(NotificationRedis redis, String key);
    void saveNotificationForPersonal(NotificationRedis redis, User user);
    void saveNotificationForPersonal(NotificationRedis redis, Long userId);
    void saveSubscribe(User user, String key);
    void saveUnsubscribe(User user, String key);
    List<NotificationRedis> getNotificationList(User user);
    void readNotification(User user, Long id);
    void deleteNotification(User user, Long id);
    boolean isSubscribe(Long userId, String topic);
    Page<ResponseNotificationRedisDTO> getNotificationRedisList(int page, int size, User user);
}
