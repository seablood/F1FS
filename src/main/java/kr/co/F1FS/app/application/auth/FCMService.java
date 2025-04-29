package kr.co.F1FS.app.application.auth;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.fcm.FCMNotificationRepository;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import kr.co.F1FS.app.presentation.fcm.dto.FCMTopicRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {
    private final FCMNotificationRepository fcmNotificationRepository;

    @Transactional
    public void save(User user, String token){
        FCMNotification notification = FCMNotification.builder()
                .userId(user.getId())
                .token(token)
                .build();

        fcmNotificationRepository.save(notification);
    }

    public void sendPushToTopic(FCMPushDTO dto){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        Message message = Message.builder()
                .setTopic(dto.getTopic())
                .setNotification(notification)
                .build();

        try{
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("토픽 푸시 알림 전송 성공 : {}", response);
        } catch (Exception e){
            log.error("토픽 푸시 알림 전송 실패");
        }
    }

    public void subscribeToTopic(FCMTopicRequestDTO dto){
        try{
            FirebaseMessaging.getInstance().subscribeToTopic(List.of(dto.getToken()), dto.getTopic());
        } catch (Exception e){
            log.error("토픽 구독 지정 실패");
        }
    }

    public void unsubscribeFromTopic(FCMTopicRequestDTO dto){
        try{
            FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(dto.getToken()), dto.getTopic());
        } catch (Exception e){
            log.error("토픽 구독 해제 실패");
        }
    }

    @Transactional
    public void deleteToken(Long userId){
        FCMNotification notification = fcmNotificationRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("FCM 토큰이 없습니다."));

        fcmNotificationRepository.delete(notification);
    }
}
