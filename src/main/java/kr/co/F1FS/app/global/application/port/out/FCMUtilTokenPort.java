package kr.co.F1FS.app.global.application.port.out;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;

public interface FCMUtilTokenPort {
    FCMToken findByUserId(Long userId);
    FCMToken findByUserIdOrNull(Long userId);
}
