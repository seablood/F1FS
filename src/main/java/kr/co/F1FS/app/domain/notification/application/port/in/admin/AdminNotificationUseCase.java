package kr.co.F1FS.app.domain.notification.application.port.in.admin;

import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import org.springframework.data.domain.Page;

public interface AdminNotificationUseCase {
    void addPushDTO(FCMPushDTO pushDTO, User adminUser);
    void sendPushForLiveInfo(FCMPushDTO pushDTO, User adminUser);
    Page<SimpleResponseNotificationDTO> getNotificationListByAuthor(int page, int size, String condition, User adminUser);
    ResponseNotificationDTO getNotificationById(Long id);
    ResponseNotificationDTO modify(Long id, ModifyNotificationDTO dto);
    void delete(Long id);
}
