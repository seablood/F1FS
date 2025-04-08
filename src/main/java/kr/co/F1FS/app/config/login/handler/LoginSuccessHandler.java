package kr.co.F1FS.app.config.login.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.config.auth.PrincipalDetails;
import kr.co.F1FS.app.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.UserRepository;
import kr.co.F1FS.app.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    private static final Duration ACCESS_DURATION = Duration.ofMinutes(30);
    private static final Duration REFRESH_DURATION = Duration.ofDays(7);

    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = userRepository.findByUsername(getUsername(authentication))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        String accessToken = jwtTokenService.createToken(user, ACCESS_DURATION);
        String refreshToken = jwtTokenService.createToken(user, REFRESH_DURATION);

        jwtTokenService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        saveRefreshToken(user, refreshToken);
        addRefreshTokenCookie(request, response, refreshToken);

        log.info("로그인 계정 : "+user.getUsername());
        log.info("Access Token : "+accessToken);
        log.info("Refresh Token : "+refreshToken);
    }

    public void saveRefreshToken(User user, String refreshToken){
        user.updateRefreshToken(refreshToken);
        user.updateLastLoginDate();
        userRepository.saveAndFlush(user);
    }

    public void addRefreshTokenCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken){
        int maxAge = (int) REFRESH_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, maxAge, true);
    }

    public String getUsername(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        return principalDetails.getUsername();
    }
}
