package kr.co.F1FS.app.presentation.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.application.notification.FCMGroupService;
import kr.co.F1FS.app.application.notification.NotificationService;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import kr.co.F1FS.app.presentation.fcm.dto.FCMTopicRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
@Tag(name = "FCM 시스템", description = "FCM 토큰 발급 및 푸시 알림 서비스")
public class FCMController {
    private final FCMGroupService fcmGroupService;
    private final NotificationService notificationService;

    @PostMapping("/token/save")
    @Operation(summary = "FCM 토큰 발급", description = "발급된 FCM 토큰을 저장")
    public ResponseEntity<Void> save(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @RequestBody String token){
        fcmGroupService.save(principalDetails.getUser(), token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/push/public-notify")
    @Operation(summary = "오피셜 공지/알림 저장", description = "오피셜 공지/알림을 작성해 저장")
    public ResponseEntity<Void> savePublicNotify(@RequestBody FCMPushDTO pushDTO){
        fcmGroupService.addPushDTO(pushDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/subscribe")
    @Operation(summary = "토픽 구독 지정", description = "특정 토픽을 구독")
    public ResponseEntity<Void> subscribeToTopic(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                 @RequestBody FCMTopicRequestDTO dto){
        fcmGroupService.subscribeToTopic(dto);
        notificationService.saveSubscribeTopic(principalDetails.getUser(), dto.getTopic());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/unsubscribe")
    @Operation(summary = "토픽 구독 해제", description = "특정 토픽 구독 해제")
    public ResponseEntity<Void> unsubscribeFromTopic(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                     @RequestBody FCMTopicRequestDTO dto){
        fcmGroupService.unsubscribeFromTopic(dto);
        notificationService.saveUnSubscribeTopic(principalDetails.getUser(), dto.getTopic());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
