package kr.co.F1FS.app.domain.notification.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMGroupUseCase;
import kr.co.F1FS.app.domain.notification.application.service.FCMTokenService;
import kr.co.F1FS.app.domain.notification.application.service.NotificationRedisService;
import kr.co.F1FS.app.domain.notification.application.service.NotificationService;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMTopicRequestDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationRedisDTO;
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
@RequestMapping("/api/v1/fcm")
@Tag(name = "FCM 시스템", description = "FCM 토큰 발급 및 푸시 알림 서비스")
public class FCMController {
    private final FCMGroupUseCase fcmGroupService;
    private final NotificationRedisService redisService;
    private final NotificationService notificationService;
    private final FCMTokenService fcmTokenService;

    @PostMapping("/token/save")
    @Operation(summary = "FCM 토큰 발급", description = "발급된 FCM 토큰을 저장")
    public ResponseEntity<Void> save(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @RequestBody String token){
        fcmTokenService.save(principalDetails.getUser(), token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/subscribe")
    @Operation(summary = "토픽 구독 지정", description = "특정 토픽을 구독")
    public ResponseEntity<Void> subscribeToTopic(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                 @Valid @RequestBody FCMTopicRequestDTO dto){
        fcmGroupService.subscribeToTopic(dto, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/unsubscribe")
    @Operation(summary = "토픽 구독 해제", description = "특정 토픽 구독 해제")
    public ResponseEntity<Void> unsubscribeFromTopic(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                     @Valid @RequestBody FCMTopicRequestDTO dto){
        fcmGroupService.unsubscribeFromTopic(dto, principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/notification-read/{notificationId}")
    @Operation(summary = "공지/알림 읽음 처리", description = "특정 공지/알림에 대해 읽음 처리")
    public ResponseEntity<Void> updateIsRead(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                             @PathVariable Long notificationId){
        redisService.readNotification(principalDetails.getUser(), notificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/notify-list")
    @Operation(summary = "공지/알림 리스트", description = "로그인 유저 공지/알림 전체 리스트 반환")
    public ResponseEntity<List<ResponseNotificationRedisDTO>> getNotificationRedisList(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size){
        Page<ResponseNotificationRedisDTO> newPage = redisService.getNotificationRedisList(page, size,
                principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/notify-list/public")
    @Operation(summary = "공지/알림(public) 리스트", description = "공지/알림(public) 전체 리스트 반환")
    public ResponseEntity<List<SimpleResponseNotificationDTO>> getNotificationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size){
        Page<SimpleResponseNotificationDTO> newPage = notificationService.getNotificationList(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/public-notify/{redisId}")
    @Operation(summary = "공지 상세 페이지", description = "특정 공지의 상세 내용을 반환")
    public ResponseEntity<ResponseNotificationDTO> getNotificationByRedisId(@PathVariable Long redisId){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotificationByRedisId(redisId));
    }

    @GetMapping("/find/public-notify/id/{id}")
    @Operation(summary = "공지 상세 페이지(ID)", description = "특정 공지의 상세 내용을 반환(ID)")
    public ResponseEntity<ResponseNotificationDTO> getNotificationById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotificationById(id));
    }

    @DeleteMapping("/delete/{notificationId}")
    @Operation(summary = "공지/알림 삭제", description = "특정 공지/알림을 삭제")
    public ResponseEntity<Void> deleteNotification(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                   @PathVariable Long notificationId){
        redisService.deleteNotification(principalDetails.getUser(), notificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/token/delete")
    @Operation(summary = "FCM 토큰 삭제", description = "DB 상에서 FCM 토큰을 삭제")
    public ResponseEntity<Void> deleteToken(@AuthenticationPrincipal PrincipalDetails principalDetails){
        fcmTokenService.deleteToken(principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
