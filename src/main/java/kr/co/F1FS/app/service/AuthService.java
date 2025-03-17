package kr.co.F1FS.app.service;

import io.jsonwebtoken.Claims;
import kr.co.F1FS.app.config.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final BlackListService blackListService;
    private final JwtTokenService jwtTokenService;

    public void logout(String accessToken) {
        try {
            Claims claims = jwtTokenService.getClaims(accessToken);

            Date expiration = claims.getExpiration();
            long expirationMillis = expiration.getTime() - System.currentTimeMillis();

            // 만료 시간이 남아 있는 경우 블랙리스트 추가
            if (expirationMillis > 0) {
                blackListService.addBlackList(accessToken, expirationMillis);
            }

        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }
}
