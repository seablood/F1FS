package kr.co.F1FS.app.domain.notification.infrastructure.adapter;

import kr.co.F1FS.app.domain.notification.application.service.FCMLiveService;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeFCMLivePort;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeFCMLiveAdapter implements PostLikeFCMLivePort {
    private final FCMLiveService fcmLiveService;

    @Override
    public void sendPushForAuthor(FCMPushDTO dto, FCMToken token, User user, Long contentId) {
        fcmLiveService.sendPushForAuthor(dto, token, user, contentId);
    }
}
