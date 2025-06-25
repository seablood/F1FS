package kr.co.F1FS.app.domain.post.application.port.out;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface PostLikeFCMLivePort {
    void sendPushForAuthor(FCMPushDTO dto, FCMToken token, User user, Long contentId);
}
