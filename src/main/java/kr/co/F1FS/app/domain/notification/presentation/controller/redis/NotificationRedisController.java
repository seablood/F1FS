package kr.co.F1FS.app.domain.notification.presentation.controller.redis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.NotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.presentation.dto.fcm.FCMTopicRequestDTO;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice-redis")
@Tag(name = "Notification Redis 시스템", description = "Notification 관련 Redis 서비스")
public class NotificationRedisController {
    private final NotificationRedisUseCase notificationRedisUseCase;

    @PostMapping("/subscribe")
    @Operation(summary = "토픽 구독 지정", description = "특정 토픽을 구독")
    public ResponseEntity<Void> subscribeToTopic(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                 @Valid @RequestBody FCMTopicRequestDTO dto){
        notificationRedisUseCase.subscribeToTopic(dto, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/unsubscribe")
    @Operation(summary = "토픽 구독 해제", description = "특정 토픽 구독 해제")
    public ResponseEntity<Void> unsubscribeFromTopic(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                     @Valid @RequestBody FCMTopicRequestDTO dto){
        notificationRedisUseCase.unsubscribeFromTopic(dto, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/notification-read/{notificationId}")
    @Operation(summary = "공지/알림 읽음 처리", description = "특정 공지/알림에 대해 읽음 처리")
    public ResponseEntity<Void> updateIsRead(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @PathVariable Long notificationId){
        notificationRedisUseCase.readNotification(principalDetails.getUser(), notificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/notify-list")
    @Operation(summary = "공지/알림 리스트", description = "로그인 유저 공지/알림 전체 리스트 반환")
    public ResponseEntity<List<ResponseNotificationRedisDTO>> getNotificationRedisList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                       @RequestParam(value = "size", defaultValue = "10") int size){
        Page<ResponseNotificationRedisDTO> newPage = notificationRedisUseCase.getNotificationRedisList(page, size,
                principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @DeleteMapping("/delete/{notificationId}")
    @Operation(summary = "공지/알림 삭제", description = "특정 공지/알림을 삭제")
    public ResponseEntity<Void> deleteNotification(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                   @PathVariable Long notificationId){
        notificationRedisUseCase.deleteNotification(principalDetails.getUser(), notificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
