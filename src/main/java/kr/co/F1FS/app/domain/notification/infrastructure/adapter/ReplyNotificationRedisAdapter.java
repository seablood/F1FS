package kr.co.F1FS.app.domain.notification.infrastructure.adapter;

import kr.co.F1FS.app.domain.notification.application.service.NotificationRedisService;
import kr.co.F1FS.app.domain.reply.application.port.out.ReplyNotificationRedisPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyNotificationRedisAdapter implements ReplyNotificationRedisPort {
    private final NotificationRedisService redisService;

    @Override
    public boolean isSubscribe(Long userId, String topic) {
        return redisService.isSubscribe(userId, topic);
    }
}
