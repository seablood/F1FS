package kr.co.F1FS.app.domain.notification.application.service.push;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.co.F1FS.app.domain.notification.application.mapper.notice.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SaveNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.CreateNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMGroupUseCase;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
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
    private final SaveNotificationRedisUseCase saveNotificationRedisUseCase;
    private final CreateNotificationUseCase createNotificationUseCase;
    private final NotificationMapper notificationMapper;

    private static final BlockingQueue<FCMPushDTO> QUEUE = new LinkedBlockingQueue<>();
    private static final int MAX = 5;

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
                createNotificationUseCase.save(redis, pushDTO.getContent());
                saveNotificationRedisUseCase.saveNotification(redis, "topic:"+redis.getTopic());
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
}
