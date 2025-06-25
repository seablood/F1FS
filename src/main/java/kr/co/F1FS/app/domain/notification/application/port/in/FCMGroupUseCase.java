package kr.co.F1FS.app.domain.notification.application.port.in;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMTopicRequestDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.concurrent.atomic.AtomicLong;

public interface FCMGroupUseCase {
    void addPushDTO(FCMPushDTO pushDTO);
    void sendPush();
    void save(User user, String token);
    NotificationRedis sendPushToTopic(FCMPushDTO dto);
    void subscribeToTopic(FCMTopicRequestDTO dto, User user);
    void unsubscribeFromTopic(FCMTopicRequestDTO dto, User user);
    void deleteToken(User user);
}
