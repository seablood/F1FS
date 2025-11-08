package kr.co.F1FS.app.domain.notification.application.port.out;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;

public interface FCMTokenJpaPort {
    FCMToken save(FCMToken fcmToken);
    FCMToken findByUserIdOrNull(Long userId);
    FCMToken findByUserId(Long userId);
    void delete(FCMToken fcmToken);
}
