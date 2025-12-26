package kr.co.F1FS.app.domain.notification.application.service.fcmToken;

import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.QueryFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.out.fcmToken.FCMTokenJpaPort;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryFCMTokenService implements QueryFCMTokenUseCase {
    private final FCMTokenJpaPort fcmTokenJpaPort;

    @Override
    public FCMToken findByUserIdOrNull(Long userId) {
        return fcmTokenJpaPort.findByUserIdOrNull(userId);
    }

    @Override
    public FCMToken findByUserId(Long userId) {
        return fcmTokenJpaPort.findByUserId(userId);
    }
}
