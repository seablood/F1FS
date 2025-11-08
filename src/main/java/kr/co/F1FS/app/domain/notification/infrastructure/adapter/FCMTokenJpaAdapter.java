package kr.co.F1FS.app.domain.notification.infrastructure.adapter;

import kr.co.F1FS.app.domain.notification.application.port.out.FCMTokenJpaPort;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.FCMTokenRepository;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FCMTokenJpaAdapter implements FCMTokenJpaPort {
    private final FCMTokenRepository fcmTokenRepository;

    @Override
    public FCMToken save(FCMToken fcmToken) {
        return fcmTokenRepository.save(fcmToken);
    }

    @Override
    public FCMToken findByUserIdOrNull(Long userId) {
        return fcmTokenRepository.findByUserId(userId)
                .orElse(null);
    }

    @Override
    public FCMToken findByUserId(Long userId) {
        return fcmTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));
    }

    @Override
    public void delete(FCMToken fcmToken) {
        fcmTokenRepository.delete(fcmToken);
    }
}
