package kr.co.F1FS.app.domain.notification.application.service;

import com.google.firebase.messaging.FirebaseMessaging;
import kr.co.F1FS.app.domain.notification.application.mapper.FCMTokenMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.FCMTokenRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMTokenService implements FCMTokenUseCase {
    private final FCMTokenRepository fcmTokenRepository;
    private final NotificationRedisService redisService;
    private final FCMTokenMapper fcmTokenMapper;

    private static final String[] TOPIC_LIST = {"news", "post", "reply", "like", "note"};

    @Override
    @Transactional
    public void save(User user, String token) {
        FCMToken fcmToken = fcmTokenMapper.toFCMToken(user, token);

        for (String topic : TOPIC_LIST){
            if(redisService.isSubscribe(user.getId(), topic)){
                try{
                    FirebaseMessaging.getInstance().subscribeToTopic(List.of(fcmToken.getToken()), topic);
                    log.info("토픽 구독 지정 성공 : {}", topic);
                } catch (Exception e){
                    log.error("토픽 구독 지정 실패 : {}", topic);
                }
            }
        }

        fcmTokenRepository.save(fcmToken);
    }

    @Override
    public FCMToken findByUserIdOrNull(Long userId) {
        return fcmTokenRepository.findByUserId(userId)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteToken(User user) {
        FCMToken fcmToken = fcmTokenRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));

        for (String topic : TOPIC_LIST){
            if(redisService.isSubscribe(user.getId(), topic)){
                try {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(fcmToken.getToken()), topic);
                    log.info("토픽 구독 해제 성공 : {}", topic);
                } catch (Exception e){
                    log.error("토픽 구독 해제 실패 : {}", topic);
                }
            }
        }

        fcmTokenRepository.delete(fcmToken);
    }
}
