package kr.co.F1FS.app.domain.notification.infrastructure.adapter;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.FCMTokenRepository;
import kr.co.F1FS.app.global.application.port.out.FCMUtilTokenPort;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FCMUtilTokenAdapter implements FCMUtilTokenPort {
    private final FCMTokenRepository fcmTokenRepository;


    @Override
    public FCMToken findByUserId(Long userId) {
        return fcmTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));
    }

    @Override
    public FCMToken findByUserIdOrNull(Long userId) {
        return fcmTokenRepository.findByUserId(userId)
                .orElse(null);
    }
}
