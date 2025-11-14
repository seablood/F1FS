package kr.co.F1FS.app.domain.notification.application.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMGroupUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.NotificationUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMTopicRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMGroupService implements FCMGroupUseCase {
    private final NotificationMapper notificationMapper;
    private final NotificationRedisUseCase redisUseCase;
    private final NotificationUseCase notificationUseCase;
    private final FCMTokenUseCase fcmTokenUseCase;

    private static final BlockingQueue<FCMPushDTO> QUEUE = new LinkedBlockingQueue<>();
    private static final int MAX = 5;
    private static final String TOPIC_PREFIX = "topic:";

    @Async
    public void addPushDTO(FCMPushDTO pushDTO){
        QUEUE.add(pushDTO);
    }

    @Async
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void sendPush(){
        log.info("그룹 푸시 알림 전송 중...");
        List<FCMPushDTO> list = new ArrayList<>();
        FCMPushDTO dto;

        while (!QUEUE.isEmpty()){
            dto = QUEUE.poll();
            list.add(dto);

            if(list.size() >= MAX){
                break;
            }
        }

        if(!list.isEmpty()){
            for (FCMPushDTO pushDTO : list){
                NotificationRedis redis = sendPushToTopic(pushDTO);
                notificationUseCase.saveNotification(redis, pushDTO.getContent());
                redisUseCase.saveNotification(redis, "topic:"+redis.getTopic());
            }
        }

        log.info("그룹 푸시 알림 전송 완료");
    }

    @Override
    public NotificationRedis sendPushToTopic(FCMPushDTO dto){ // 토픽 구독자 전체에게 푸시 알림 발송
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
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("토픽 푸시 알림 전송 성공 : {}", response);
        } catch (Exception e){
            log.error("토픽 푸시 알림 전송 실패");
            QUEUE.add(dto);
        }

        return redis;
    }

    @Override
    public void subscribeToTopic(FCMTopicRequestDTO dto, User user){
        String key = TOPIC_PREFIX+dto.getTopic();
        FCMToken token = fcmTokenUseCase.findByUserIdOrNull(user.getId());

        if(token != null){
            try{
                FirebaseMessaging.getInstance().subscribeToTopic(List.of(token.getToken()), dto.getTopic());
                redisUseCase.saveSubscribe(user, key);
                log.info("토픽 구독 지정 성공");
            } catch (Exception e){
                log.error("토픽 구독 지정 실패");
            }
        }else {
            redisUseCase.saveSubscribe(user, key);
        }
    }

    @Override
    public void unsubscribeFromTopic(FCMTopicRequestDTO dto, User user){
        String key = TOPIC_PREFIX+dto.getTopic();
        FCMToken token = fcmTokenUseCase.findByUserIdOrNull(user.getId());

        if(token != null){
            try{
                FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(token.getToken()), dto.getTopic());
                redisUseCase.saveUnsubscribe(user, key);
            } catch (Exception e){
                log.error("토픽 구독 해제 실패");
            }
        }else {
            redisUseCase.saveUnsubscribe(user, key);
        }
    }
}
