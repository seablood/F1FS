package kr.co.F1FS.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.dto.CreateUserDTO;
import kr.co.F1FS.app.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.service.AuthService;
import kr.co.F1FS.app.util.CookieUtil;
import kr.co.F1FS.app.util.user.UserException;
import kr.co.F1FS.app.util.user.UserExceptionType;
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
    private final AuthService authService;

    @PostMapping("/user-save")
    @Operation(summary = "회원가입(자체 계정)", description = "자체 로그인 계정 생성")
    public ResponseEntity<User> save(@RequestBody @Valid CreateUserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.save(userDTO));
    }

    @PostMapping("/verify-code")
    @Operation(summary = "인증 코드 검사", description = "인증 코드 유효성을 검사")
    public ResponseEntity<Void> verifyCode(@RequestParam(value = "email") String email,
                                           @RequestParam(value = "code") String code){
        authService.verifyCode(email, code);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/update-password")
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 후 저장")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @RequestBody @Valid ModifyPasswordDTO passwordDTO){
        authService.updatePassword(principalDetails.getUser(), passwordDTO);
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

        authService.logout(accessToken, refreshToken, request, response, principalDetails.getUser());

        return ResponseEntity.ok("로그아웃 완료");
    }
}
