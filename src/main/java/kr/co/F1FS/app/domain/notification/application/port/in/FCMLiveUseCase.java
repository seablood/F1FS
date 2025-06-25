package kr.co.F1FS.app.domain.notification.application.port.in;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FCMLiveUseCase {
    NotificationRedis sendPushForLiveInfo(FCMPushDTO dto);
    void sendPushForAuthor(FCMPushDTO dto, FCMToken token, User user, Long contentId);
    void sendPushForFollow(FCMPushDTO dto, List<FCMToken> tokens, Long contentId);

}
