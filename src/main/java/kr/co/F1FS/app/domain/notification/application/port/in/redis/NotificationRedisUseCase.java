package kr.co.F1FS.app.domain.notification.application.port.in.redis;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.presentation.dto.fcm.FCMTopicRequestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationRedisUseCase {
    void subscribeToTopic(FCMTopicRequestDTO dto, User user);
    void unsubscribeFromTopic(FCMTopicRequestDTO dto, User user);
    void readNotification(User user, Long id);
    void deleteNotification(User user, Long id);
    Page<ResponseNotificationRedisDTO> getNotificationRedisList(int page, int size, User user);
}
