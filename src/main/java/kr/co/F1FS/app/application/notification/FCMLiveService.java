package kr.co.F1FS.app.application.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import kr.co.F1FS.app.domain.model.redis.NotificationRedis;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMLiveService {
    private final NotificationRedisService redisService;
    private final FCMGroupService fcmGroupService;

    public NotificationRedis sendPushForLiveInfo(FCMPushDTO dto){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        Message message = Message.builder()
                .setTopic(dto.getTopic())
                .setNotification(notification)
                .build();

        NotificationRedis redis = NotificationRedis.builder()
                .id(fcmGroupService.getSequence().getAndAdd(1L))
                .title(dto.getTitle())
                .content(dto.getContent())
                .topic(dto.getTopic())
                .build();

        try{
            String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
            redisService.saveNotification(redis, "topic:"+dto.getTopic());
            log.info("토픽 푸시 알림 전송 성공 : {}", response);
        } catch (Exception e){
            log.error("토픽 푸시 알림 전송 실패");
        }

        return redis;
    }

    public void sendPushForAuthor(FCMPushDTO dto, FCMNotification token, Long contentId){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        NotificationRedis redis = NotificationRedis.builder()
                .id(fcmGroupService.getSequence().getAndAdd(1L))
                .title(dto.getTitle())
                .content(dto.getContent())
                .topic(dto.getTopic())
                .build();

        redis.setContentId(contentId);

        Message message = Message.builder()
                .setToken(token.getToken())
                .setNotification(notification)
                .build();

        try{
            String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
            redisService.saveNotificationForPersonal(redis, token.getUserId());
            log.info("토픽 푸시 알림 전송 성공 : {}", response);
        } catch (Exception e){
            log.error("토픽 푸시 알림 전송 실패");
        }
    }

    public void sendPushForFollow(FCMPushDTO dto, List<FCMNotification> tokens, Long contentId){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        NotificationRedis redis = NotificationRedis.builder()
                .id(fcmGroupService.getSequence().getAndAdd(1L))
                .title(dto.getTitle())
                .content(dto.getContent())
                .topic(dto.getTopic())
                .build();

        redis.setContentId(contentId);

        for (FCMNotification token : tokens){
            Message message = Message.builder()
                    .setToken(token.getToken())
                    .setNotification(notification)
                    .build();

            try{
                String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
                redisService.saveNotificationForPersonal(redis, token.getUserId());
                log.info("토픽 푸시 알림 전송 성공 : {}", response);
            } catch (Exception e){
                log.error("토픽 푸시 알림 전송 실패");
            }
        }
    }
}
