package kr.co.F1FS.app.domain.reply.application.port.out;

public interface ReplyNotificationRedisPort {
    boolean isSubscribe(Long userId, String topic);
}
