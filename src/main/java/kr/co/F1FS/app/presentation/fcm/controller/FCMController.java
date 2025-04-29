package kr.co.F1FS.app.presentation.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.F1FS.app.application.auth.FCMService;
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
    private final FCMService fcmService;

    @PostMapping("/token/save")
    @Operation(summary = "FCM 토큰 발급", description = "발급된 FCM 토큰을 저장")
    public ResponseEntity<Void> save(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @RequestBody String token){
        fcmService.save(principalDetails.getUser(), token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/notify-topic")
    @Operation(summary = "푸시 알림 전송", description = "특정 토픽에 대한 푸시 알림 전송")
    public ResponseEntity<Void> sendPushToTopic(@RequestBody @Valid FCMPushDTO dto){
        fcmService.sendPushToTopic(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/subscribe")
    @Operation(summary = "토픽 구독 지정", description = "특정 토픽을 구독")
    public ResponseEntity<Void> subscribeToTopic(@RequestBody FCMTopicRequestDTO dto){
        fcmService.subscribeToTopic(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/unsubscribe")
    @Operation(summary = "토픽 구독 해제", description = "특정 토픽 구독 해제")
    public ResponseEntity<Void> unsubscribeFromTopic(@RequestBody FCMTopicRequestDTO dto){
        fcmService.unsubscribeFromTopic(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
