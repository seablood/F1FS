package kr.co.F1FS.app.domain.notification.application.mapper;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import kr.co.F1FS.app.global.util.NotificationType;
import kr.co.F1FS.app.global.util.TimeUtil;
import kr.co.F1FS.app.global.util.exception.notification.NotificationException;
import kr.co.F1FS.app.global.util.exception.notification.NotificationExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
public class NotificationMapper {
    private AtomicLong sequence = new AtomicLong(1L);

    public FCMToken toFCMToken(User user, String token){
        return FCMToken.builder()
                .userId(user.getId())
                .token(token)
                .build();
    }

    public NotificationRedis toNotificationRedis(FCMPushDTO dto, String option){
        switch (option){
            case "official" :
                return NotificationRedis.builder()
                        .id(sequence.getAndAdd(1L))
                        .title(dto.getTitle())
                        .content(dto.getSimpleContent())
                        .topic(dto.getTopic())
                        .type(NotificationType.OFFICIAL)
                        .build();
            case "personal" :
                return NotificationRedis.builder()
                        .id(sequence.getAndAdd(1L))
                        .title(dto.getTitle())
                        .content(dto.getSimpleContent())
                        .topic(dto.getTopic())
                        .type(NotificationType.PERSONAL)
                        .build();
            default:
                throw new NotificationException(NotificationExceptionType.NOTIFICATION_TYPE_ERROR);
        }
    }

    public Notification toNotification(NotificationRedis redis, String content){
        return Notification.builder()
                .redis(redis)
                .content(content)
                .build();
    }

    public ResponseNotificationDTO toResponseNotificationDTO(Notification notification){
        LocalDateTime notificationTime = TimeUtil.convertToKoreanTime(notification.getCreatedAt());

        return ResponseNotificationDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .topic(notification.getTopic())
                .createdAt(TimeUtil.formatPostTime(notificationTime))
                .build();
    }

    public ResponseNotificationRedisDTO toResponseNotificationRedisDTO(NotificationRedis redis){
        LocalDateTime notificationTime = TimeUtil.convertToKoreanTime(redis.getCreatedAt());

        return ResponseNotificationRedisDTO.builder()
                .id(redis.getId())
                .title(redis.getTitle())
                .content(redis.getContent())
                .contentId(redis.getContentId())
                .type(redis.getType().getType())
                .isRead(redis.isRead())
                .createdAt(TimeUtil.formatPostTime(notificationTime))
                .build();
    }

    public SimpleResponseNotificationDTO toSimpleResponseNotificationDTO(Notification notification){
        LocalDateTime notificationTime = TimeUtil.convertToKoreanTime(notification.getCreatedAt());

        return SimpleResponseNotificationDTO.builder()
                .id(notification.getId())
                .redisId(notification.getRedisId())
                .title(notification.getTitle())
                .simpleContent(notification.getSimpleContent())
                .topic(notification.getTopic())
                .createdAt(TimeUtil.formatPostTime(notificationTime))
                .build();
    }
}
