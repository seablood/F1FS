package kr.co.F1FS.app.presentation.admin.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.application.notification.FCMGroupService;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/fcm")
@Tag(name = "FCM 컨트롤러(관리자 권한)", description = "FCM 푸시 알림 서비스(관리자 권한)")
public class AdminFCMController {
    private final FCMGroupService fcmGroupService;

    @PostMapping("/push/public-notify")
    @Operation(summary = "오피셜 공지/알림 저장", description = "오피셜 공지/알림을 작성해 저장")
    public ResponseEntity<Void> savePublicNotify(@RequestBody FCMPushDTO pushDTO){
        fcmGroupService.addPushDTO(pushDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
