package kr.co.F1FS.app.domain.notification.presentation.controller.fcmToken;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.FCMTokenUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm-token")
@Tag(name = "FCM 토큰 시스템", description = "FCM 토큰 발급 서비스")
public class FCMTokenController {
    private final FCMTokenUseCase fcmTokenUseCase;

    @PostMapping("/save")
    @Operation(summary = "FCM 토큰 발급", description = "발급된 FCM 토큰을 저장")
    public ResponseEntity<Void> save(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody String token){
        fcmTokenUseCase.save(principalDetails.getUser(), token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "FCM 토큰 삭제", description = "DB 상에서 FCM 토큰을 삭제")
    public ResponseEntity<Void> deleteToken(@AuthenticationPrincipal PrincipalDetails principalDetails){
        fcmTokenUseCase.deleteToken(principalDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
