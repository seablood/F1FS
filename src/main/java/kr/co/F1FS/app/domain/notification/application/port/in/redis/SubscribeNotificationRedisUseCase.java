package kr.co.F1FS.app.domain.notification.application.port.in.redis;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.fcm.FCMTopicRequestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.Topic;

public interface SubscribeNotificationRedisUseCase {
    void saveSubscribe(FCMTopicRequestDTO dto, User user, String key, FCMToken token);
    void saveUnsubscribe(FCMTopicRequestDTO dto, User user, String key, FCMToken token);
    boolean isSubscribe(Long userId, Topic topic);
}
