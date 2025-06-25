package kr.co.F1FS.app.domain.notification.application.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.notification.application.mapper.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMGroupUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.FCMTokenRepository;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
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
    private final NotificationRedisService redisService;
    private final NotificationService notificationService;
    private final FCMTokenRepository fcmTokenRepository;

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
                notificationService.saveNotification(redis, pushDTO.getContent());
                redisService.saveNotification(redis, "topic:"+redis.getTopic());
            }
        }

        log.info("그룹 푸시 알림 전송 완료");
    }

    @Transactional
    public void save(User user, String token){
        FCMToken fcmToken = notificationMapper.toFCMToken(user, token);

        fcmTokenRepository.save(fcmToken);
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

    public void subscribeToTopic(FCMTopicRequestDTO dto, User user){
        String key = TOPIC_PREFIX+dto.getTopic();
        FCMToken token = fcmTokenRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));

        try{
            FirebaseMessaging.getInstance().subscribeToTopic(List.of(token.getToken()), dto.getTopic());
            redisService.saveSubscribe(user, key);
            log.info("토픽 구독 지정 성공");
        } catch (Exception e){
            log.error("토픽 구독 지정 실패");
        }
    }

    public void unsubscribeFromTopic(FCMTopicRequestDTO dto, User user){
        String key = TOPIC_PREFIX+dto.getTopic();
        FCMToken token = fcmTokenRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));

        try{
            FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(token.getToken()), dto.getTopic());
            redisService.saveUnsubscribe(user, key);
        } catch (Exception e){
            log.error("토픽 구독 해제 실패");
        }
    }

    @Transactional
    public void deleteToken(User user) {
        FCMToken fcmToken = fcmTokenRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));

        fcmTokenRepository.delete(fcmToken);
    }
}
