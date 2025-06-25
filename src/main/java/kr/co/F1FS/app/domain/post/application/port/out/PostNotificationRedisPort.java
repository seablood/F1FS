package kr.co.F1FS.app.domain.post.application.port.out;

public interface PostNotificationRedisPort {
    boolean isSubscribe(Long userId, String topic);
}
