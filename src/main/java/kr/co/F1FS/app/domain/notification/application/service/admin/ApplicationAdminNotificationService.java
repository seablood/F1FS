package kr.co.F1FS.app.domain.notification.application.service.admin;

import kr.co.F1FS.app.domain.notification.application.port.in.admin.AdminNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.DeleteNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.QueryNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.UpdateNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMGroupUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminNotificationService implements AdminNotificationUseCase {
    private final UpdateNotificationUseCase updateNotificationUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;
    private final QueryNotificationUseCase queryNotificationUseCase;
    private final FCMGroupUseCase fcmGroupUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;

    @Override
    public void addPushDTO(FCMPushDTO pushDTO, User adminUser) {
        pushDTO.setAuthor(adminUser.getNickname());
        fcmGroupUseCase.addPushDTO(pushDTO);
    }

    @Override
    public void sendPushForLiveInfo(FCMPushDTO pushDTO, User adminUser) {
        pushDTO.setAuthor(adminUser.getNickname());
        fcmLiveUseCase.sendPushForLiveInfo(pushDTO);
    }

    @Override
    public Page<SimpleResponseNotificationDTO> getNotificationListByAuthor(int page, int size, String condition, User adminUser) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryNotificationUseCase.findAllByAuthorForSimpleDTO(adminUser.getNickname(), pageable);
    }

    @Override
    public ResponseNotificationDTO getNotificationById(Long id) {
        return queryNotificationUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional
    public ResponseNotificationDTO modify(Long id, ModifyNotificationDTO dto){
        Notification notification = queryNotificationUseCase.findById(id);

        return updateNotificationUseCase.modify(notification, dto);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Notification notification = queryNotificationUseCase.findById(id);

        deleteNotificationUseCase.delete(notification);
    }

    public Pageable switchCondition(int page, int size, String condition){
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }
}
