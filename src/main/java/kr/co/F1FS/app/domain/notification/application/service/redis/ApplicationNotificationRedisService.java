package kr.co.F1FS.app.domain.notification.application.service.redis;

import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.QueryFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.DeleteNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.FindNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SubscribeNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.fcm.FCMTopicRequestDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationNotificationRedisService implements NotificationRedisUseCase {
    private final SubscribeNotificationRedisUseCase subscribeNotificationRedisUseCase;
    private final DeleteNotificationRedisUseCase deleteNotificationRedisUseCase;
    private final FindNotificationRedisUseCase findNotificationRedisUseCase;
    private final QueryFCMTokenUseCase queryFCMTokenUseCase;

    private static final String TOPIC_PREFIX = "topic:";

    @Override
    public void subscribeToTopic(FCMTopicRequestDTO dto, User user) {
        String key = TOPIC_PREFIX+dto.getTopic();
        FCMToken token = queryFCMTokenUseCase.findByUserIdOrNull(user.getId());

        subscribeNotificationRedisUseCase.saveSubscribe(dto, user, key, token);
    }

    @Override
    public void unsubscribeFromTopic(FCMTopicRequestDTO dto, User user) {
        String key = TOPIC_PREFIX+dto.getTopic();
        FCMToken token = queryFCMTokenUseCase.findByUserIdOrNull(user.getId());

        subscribeNotificationRedisUseCase.saveUnsubscribe(dto, user, key, token);
    }

    @Override
    public void readNotification(User user, Long id){
        findNotificationRedisUseCase.readNotification(user, id);
    }

    @Override
    public void deleteNotification(User user, Long id){
        String key = "notification:"+user.getId();

        deleteNotificationRedisUseCase.deleteNotification(user, id, key);
    }

    @Override
    public Page<ResponseNotificationRedisDTO> getNotificationRedisList(int page, int size, User user){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return findNotificationRedisUseCase.getNotificationRedisList(pageable, user);
    }
}
