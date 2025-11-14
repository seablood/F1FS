package kr.co.F1FS.app.domain.auth.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.auth.application.port.in.TokenUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
@Tag(name = "토큰 시스템", description = "토큰 재발급 등 토큰 관련 기능")
public class TokenApiController {
    private final TokenUseCase tokenUseCase;

    @PostMapping("/create-token")
    @Operation(summary = "토큰 재발급", description = "토큰을 재발급하여 저장하고 헤더에 포함하여 클라이언트 전달")
    public ResponseEntity<Void> createNewToken(HttpServletRequest request, HttpServletResponse response,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails){
        String refreshToken = CookieUtil.getCookie(request, "refresh_token");
        tokenUseCase.createNewAccessToken(principalDetails.getUser().getId(), refreshToken, response);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
