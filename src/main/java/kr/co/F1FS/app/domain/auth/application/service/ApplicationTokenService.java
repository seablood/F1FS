package kr.co.F1FS.app.domain.auth.application.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.auth.application.port.in.TokenUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ApplicationTokenService implements TokenUseCase {
    private final QueryUserUseCase queryUserUseCase;
    private final JwtTokenService jwtTokenService;

    @Override
    @Transactional
    public void createNewAccessToken(Long id, String refreshToken, HttpServletResponse response){
        if(!jwtTokenService.validateToken(refreshToken)){
            throw new UserException(UserExceptionType.TOKEN_VALIDATE_ERROR);
        } else {
            queryUserUseCase.findByRefreshToken(refreshToken)
                    .filter(user1 -> user1.getId().equals(id))
                    .ifPresentOrElse(user1 -> {
                        jwtTokenService.sendAccessAndRefreshToken(
                                response, jwtTokenService.createToken(user1, Duration.ofMinutes(30)), refreshToken);
                    }, () -> {throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);});
        }
    }
}
