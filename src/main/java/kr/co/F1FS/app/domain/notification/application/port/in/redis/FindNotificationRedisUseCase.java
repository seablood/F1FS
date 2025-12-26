package kr.co.F1FS.app.domain.notification.application.port.in.redis;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindNotificationRedisUseCase {
    List<NotificationRedis> getNotificationList(User user);
    void readNotification(User user, Long id);
    Page<ResponseNotificationRedisDTO> getNotificationRedisList(Pageable pageable, User user);
}
