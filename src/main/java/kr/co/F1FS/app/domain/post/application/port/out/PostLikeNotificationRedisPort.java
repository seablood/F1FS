package kr.co.F1FS.app.domain.post.application.port.out;

public interface PostLikeNotificationRedisPort {
    boolean isSubscribe(Long userId, String topic);
}
