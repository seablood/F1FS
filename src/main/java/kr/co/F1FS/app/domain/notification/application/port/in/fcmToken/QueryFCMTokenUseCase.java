package kr.co.F1FS.app.domain.notification.application.port.in.fcmToken;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;

public interface QueryFCMTokenUseCase {
    FCMToken findByUserIdOrNull(Long userId);
    FCMToken findByUserId(Long userId);
}
