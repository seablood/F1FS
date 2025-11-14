package kr.co.F1FS.app.domain.notification.application.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMLiveService implements FCMLiveUseCase {
    private final NotificationMapper notificationMapper;
    private final NotificationRedisUseCase redisUseCase;
    private final NotificationUseCase notificationUseCase;

    @Override
    public NotificationRedis sendPushForLiveInfo(FCMPushDTO dto){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        Message message = Message.builder()
                .setTopic(dto.getTopic())
                .setNotification(notification)
                .build();

        NotificationRedis redis = notificationMapper.toNotificationRedis(dto, "official");

        try{
            String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
            notificationUseCase.saveNotification(redis, dto.getContent());
            redisUseCase.saveNotification(redis, "topic:"+dto.getTopic());
            log.info("토픽 푸시 알림 전송 성공 : {}", response);
        } catch (Exception e){
            log.error("토픽 푸시 알림 전송 실패");
        }

        return redis;
    }

    @Override
    public void sendPushForAuthor(FCMPushDTO dto, FCMToken token, User user, Long contentId){
        NotificationRedis redis = notificationMapper.toNotificationRedis(dto, "personal");

        redis.setContentId(contentId);

        if(token != null){
            Notification notification = Notification.builder()
                    .setTitle(dto.getTitle())
                    .setBody(dto.getContent())
                    .build();

            Message message = Message.builder()
                    .setToken(token.getToken())
                    .setNotification(notification)
                    .build();

            try{
                String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
                redisUseCase.saveNotificationForPersonal(redis, user);
                log.info("토픽 푸시 알림 전송 성공 : {}", response);
            } catch (Exception e){
                log.error("토픽 푸시 알림 전송 실패");
            }
        }else {
            redisUseCase.saveNotificationForPersonal(redis, user);
        }
    }

    @Override
    public void sendPushForFollow(FCMPushDTO dto, List<FCMToken> tokens, Long contentId){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        NotificationRedis redis = notificationMapper.toNotificationRedis(dto, "personal");

        redis.setContentId(contentId);

        for (FCMToken token : tokens){
            Message message = Message.builder()
                    .setToken(token.getToken())
                    .setNotification(notification)
                    .build();

            try{
                String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
                redisUseCase.saveNotificationForPersonal(redis, token.getUserId());
                log.info("토픽 푸시 알림 전송 성공 : {}", response);
            } catch (Exception e){
                log.error("토픽 푸시 알림 전송 실패");
            }
        }
    }
}
