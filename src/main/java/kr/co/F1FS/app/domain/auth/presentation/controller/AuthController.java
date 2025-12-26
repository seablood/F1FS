package kr.co.F1FS.app.domain.auth.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.F1FS.app.domain.auth.application.port.in.auth.AuthUseCase;
import kr.co.F1FS.app.domain.auth.application.port.in.verification.VerifyAndDeleteVerificationUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.CookieUtil;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "인증 관련 서비스")
public class AuthController {
    private final JwtTokenService jwtTokenService;
    private final AuthUseCase authUseCase;
    private final VerifyAndDeleteVerificationUseCase verifyAndDeleteVerificationUseCase;

    @PostMapping("/user-save")
    @Operation(summary = "회원가입(자체 계정)", description = "자체 로그인 계정 생성")
    public ResponseEntity<ResponseUserDTO> save(@Valid @RequestBody CreateUserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(authUseCase.save(userDTO));
    }

    @PostMapping("/verify-code")
    @Operation(summary = "인증 코드 검사", description = "인증 코드 유효성을 검사")
    public ResponseEntity<Void> verifyCode(@RequestParam(value = "email") String email,
                                           @RequestParam(value = "code") String code){
        verifyAndDeleteVerificationUseCase.verifyCode(email, code);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/update-password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 후 저장")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @Valid @RequestBody ModifyPasswordDTO passwordDTO){
        authUseCase.updatePassword(principalDetails.getUser(), passwordDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 시, 토큰 블랙리스트 추가")
    public ResponseEntity<String> logout(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         HttpServletRequest request, HttpServletResponse response){
        String accessToken = jwtTokenService.resolveAccessToken(request)
                .orElse(null);
        String refreshToken = CookieUtil.getCookie(request, "refresh_token");

        if(accessToken == null || refreshToken == null)
            throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);

        authUseCase.logout(accessToken, refreshToken, request, response, principalDetails.getUser());

        return ResponseEntity.ok("로그아웃 완료");
    }

    @DeleteMapping("/secession")
    @Operation(summary = "회원 탈퇴", description = "화원 탈퇴 및 토큰 블랙리스트 추가")
    public ResponseEntity<String> secession(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                            HttpServletRequest request, HttpServletResponse response){
        String accessToken = jwtTokenService.resolveAccessToken(request)
                .orElse(null);
        String refreshToken = CookieUtil.getCookie(request, "refresh_token");

        if(accessToken == null || refreshToken == null)
            throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);

        authUseCase.secession(accessToken, refreshToken, request, response, principalDetails.getUser());

        return ResponseEntity.ok("회원 탈퇴 완료");
    }
}
