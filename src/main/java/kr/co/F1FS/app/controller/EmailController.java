package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.service.AuthService;
import kr.co.F1FS.app.service.EmailAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
@Tag(name = "Email Controller", description = "이메일 관련 서비스")
public class EmailController {
    private final EmailAsyncService emailAsyncService;
    private final AuthService authService;

    @GetMapping("/update-password")
    @Operation(summary = "비밀번호 변경 인증 메일 보내기", description = "비밀번호 변경 요청 인증 메일 송신")
    public ResponseEntity<Void> sendUpdatePasswordEmail(@AuthenticationPrincipal PrincipalDetails principalDetails){
        authService.sendEmail(principalDetails.getUser(), "update_password");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/simple")
    @Operation(summary = "심플 이메일 보내기", description = "간단한 텍스트의 메일을 전송")
    public ResponseEntity<Void> sendSimpleMessage(){
        emailAsyncService.sendSimpleMailMessage();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
