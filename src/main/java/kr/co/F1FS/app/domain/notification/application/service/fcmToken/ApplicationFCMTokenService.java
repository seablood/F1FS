package kr.co.F1FS.app.domain.notification.application.service.fcmToken;

import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.CreateFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.DeleteFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.FCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.QueryFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationFCMTokenService implements FCMTokenUseCase {
    private final CreateFCMTokenUseCase createFCMTokenUseCase;
    private final DeleteFCMTokenUseCase deleteFCMTokenUseCase;
    private final QueryFCMTokenUseCase queryFCMTokenUseCase;

    @Override
    @Transactional
    public void save(User user, String token) {
        createFCMTokenUseCase.save(user, token);
    }

    @Override
    @Transactional
    public void deleteToken(User user) {
        FCMToken fcmToken = queryFCMTokenUseCase.findByUserId(user.getId());

        deleteFCMTokenUseCase.delete(fcmToken, user);
    }
}
