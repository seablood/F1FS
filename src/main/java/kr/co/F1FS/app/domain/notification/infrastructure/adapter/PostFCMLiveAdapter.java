package kr.co.F1FS.app.domain.notification.infrastructure.adapter;

import kr.co.F1FS.app.domain.notification.application.service.FCMLiveService;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.post.application.port.out.PostFCMLivePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostFCMLiveAdapter implements PostFCMLivePort {
    private final FCMLiveService fcmLiveService;

    @Override
    public void sendPushForFollow(FCMPushDTO dto, List<FCMToken> tokens, Long contentId) {
        fcmLiveService.sendPushForFollow(dto, tokens, contentId);
    }
}
