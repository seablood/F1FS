package kr.co.F1FS.app.domain.notification.application.port.in.push;

import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;

public interface FCMGroupUseCase {
    void addPushDTO(FCMPushDTO pushDTO);
    void sendPush();
    NotificationRedis sendPushToTopic(FCMPushDTO dto);
}
