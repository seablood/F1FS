package kr.co.F1FS.app.application.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.model.redis.NotificationRedis;
import kr.co.F1FS.app.domain.repository.rdb.fcm.FCMNotificationRepository;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import kr.co.F1FS.app.presentation.fcm.dto.FCMTopicRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMGroupService {
    private final NotificationRedisService redisService;
    private final FCMNotificationRepository fcmNotificationRepository;

    private static final BlockingQueue<FCMPushDTO> QUEUE = new LinkedBlockingQueue<>();
    private static final int MAX_SIZE = 5;
    private AtomicLong sequence = new AtomicLong(1L);

    @Async
    public void addPushDTO(String topic, String title, String content){
        QUEUE.add(new FCMPushDTO(topic, title, content));
    }

    @Async
    @Scheduled(fixedRate = 10000)
    public void sendPush(){
        log.info("푸시 알림 전송 중...");
        List<FCMPushDTO> list = new ArrayList<>();
        FCMPushDTO dto;

        while (!QUEUE.isEmpty()){
            dto = QUEUE.poll();
            list.add(dto);

            if(list.size() >= MAX_SIZE){
                break;
            }
        }

        if(!list.isEmpty()){
            for (FCMPushDTO pushDTO : list){
                NotificationRedis redis = sendPushToTopic(pushDTO);
                redisService.saveNotification(redis, "topic:"+redis.getTopic());
            }
        }

        log.info("푸시 알림 전송 완료");
    }

    @Transactional
    public void save(User user, String token){
        FCMNotification notification = FCMNotification.builder()
                .userId(user.getId())
                .token(token)
                .build();

        fcmNotificationRepository.save(notification);
    }

    public NotificationRedis sendPushToTopic(FCMPushDTO dto){ // 토픽 구독자 전체에게 푸시 알림 발송
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        Message message = Message.builder()
                .setTopic(dto.getTopic())
                .setNotification(notification)
                .build();

        NotificationRedis redis = NotificationRedis.builder()
                .id(sequence.getAndAdd(1L))
                .title(dto.getTitle())
                .content(dto.getContent())
                .topic(dto.getTopic())
                .build();

        try{
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("토픽 푸시 알림 전송 성공 : {}", response);
        } catch (Exception e){
            log.error("토픽 푸시 알림 전송 실패");
            QUEUE.add(dto);
        }

        return redis;
    }

    public void subscribeToTopic(FCMTopicRequestDTO dto){
        try{
            FirebaseMessaging.getInstance().subscribeToTopic(List.of(dto.getToken()), dto.getTopic());
            log.info("토픽 구독 지정 성공");
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
