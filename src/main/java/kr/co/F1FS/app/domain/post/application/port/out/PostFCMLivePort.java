package kr.co.F1FS.app.domain.post.application.port.out;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;

import java.util.List;

public interface PostFCMLivePort {
    void sendPushForFollow(FCMPushDTO dto, List<FCMToken> tokens, Long contentId);
}
