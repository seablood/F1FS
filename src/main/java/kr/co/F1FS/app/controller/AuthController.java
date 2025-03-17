package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.F1FS.app.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "인증 관련 서비스")
public class AuthController {
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 시, 토큰 블랙리스트 추가")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String token = jwtTokenService.resolveAccessToken(request)
                .orElse(null);

        if(token == null) throw new RuntimeException("잘못된 토큰 형식");

        authService.logout(token);

        return ResponseEntity.ok("로그아웃 완료");
    }
}
