package kr.co.F1FS.app.domain.notification.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.notification.application.port.in.admin.AdminNotificationUseCase;
import kr.co.F1FS.app.domain.notification.presentation.dto.admin.ModifyNotificationDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/fcm")
@Tag(name = "FCM 컨트롤러(관리자 권한)", description = "FCM 푸시 알림 서비스(관리자 권한)")
public class AdminFCMController {
    private final AdminNotificationUseCase adminNotificationUseCase;

    @PostMapping("/push/public-notify")
    @Operation(summary = "오피셜 공지/알림 저장", description = "오피셜 공지/알림을 작성해 저장")
    public ResponseEntity<Void> savePublicNotify(@Valid @RequestBody FCMPushDTO pushDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        adminNotificationUseCase.addPushDTO(pushDTO, principalDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/push/live-notify")
    @Operation(summary = "실시간(긴급) 공지/알림 전송", description = "실시간(긴급) 공지/알림을 작성해 전송")
    public ResponseEntity<Void> pushLiveNotify(@Valid @RequestBody FCMPushDTO pushDTO, @AuthenticationPrincipal PrincipalDetails principalDetails){
        adminNotificationUseCase.sendPushForLiveInfo(pushDTO, principalDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/notification/list")
    @Operation(summary = "오피셜 및 실시간 공지 리스트", description = "로그인한 관리자가 작성한 모든 오피셜 및 실시간 공지 리스트 반환")
    public ResponseEntity<List<SimpleResponseNotificationDTO>> getNotificationListByAuthor(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                           @RequestParam(value = "condition", defaultValue = "new") String condition){
        Page<SimpleResponseNotificationDTO> newPage =
                adminNotificationUseCase.getNotificationListByAuthor(page, size, condition, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/notification/find/{id}")
    @Operation(summary = "오피셜 및 실시간 공지 상세 페이지", description = "로그인한 관리자가 작성한 오피셜 및 실시간 공지 상세 페이지 반환")
    public ResponseEntity<ResponseNotificationDTO> getNotificationById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(adminNotificationUseCase.getNotificationById(id));
    }

    @PutMapping("/notification/modify/{id}")
    @Operation(summary = "공지 수정", description = "특정 공지 수정 후 저장")
    public ResponseEntity<ResponseNotificationDTO> modifyNotification(@PathVariable Long id,
                                                                      @Valid @RequestBody ModifyNotificationDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(adminNotificationUseCase.modify(id, dto));
    }

    @DeleteMapping("/notification/delete/{id}")
    @Operation(summary = "공지 삭제", description = "특정 공지 삭제")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id){
        adminNotificationUseCase.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
