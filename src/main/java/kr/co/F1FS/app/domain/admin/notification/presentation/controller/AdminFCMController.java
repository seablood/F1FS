package kr.co.F1FS.app.domain.admin.notification.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.admin.notification.application.service.AdminNotificationService;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMGroupUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMLiveUseCase;
import kr.co.F1FS.app.domain.admin.notification.presentation.dto.ModifyNotificationDTO;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/fcm")
@Tag(name = "FCM 컨트롤러(관리자 권한)", description = "FCM 푸시 알림 서비스(관리자 권한)")
public class AdminFCMController {
    private final FCMGroupUseCase fcmGroupUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final AdminNotificationService adminNotificationService;

    @PostMapping("/push/public-notify")
    @Operation(summary = "오피셜 공지/알림 저장", description = "오피셜 공지/알림을 작성해 저장")
    public ResponseEntity<Void> savePublicNotify(@Valid @RequestBody FCMPushDTO pushDTO){
        fcmGroupUseCase.addPushDTO(pushDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/push/live-notify")
    @Operation(summary = "실시간(긴급) 공지/알림 전송", description = "실시간(긴급) 공지/알림을 작성해 전송")
    public ResponseEntity<Void> pushLiveNotify(@Valid @RequestBody FCMPushDTO pushDTO){
        fcmLiveUseCase.sendPushForLiveInfo(pushDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/notification/modify/{id}")
    @Operation(summary = "공지 수정", description = "특정 공지 수정 후 저장")
    public ResponseEntity<ResponseNotificationDTO> modifyNotification(@PathVariable Long id,
                                                                      @RequestBody ModifyNotificationDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(adminNotificationService.modify(id, dto));
    }

    @DeleteMapping("/notification/delete/{id}")
    @Operation(summary = "공지 삭제", description = "특정 공지 삭제")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id){
        adminNotificationService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
