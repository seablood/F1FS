package kr.co.F1FS.app.application.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import kr.co.F1FS.app.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.util.exception.user.UserException;
import kr.co.F1FS.app.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Transactional
    public void createNewAccessToken(Long id, String refreshToken, HttpServletResponse response){
        if(!jwtTokenService.validateToken(refreshToken)){
            throw new UserException(UserExceptionType.TOKEN_VALIDATE_ERROR);
        } else {
            userRepository.findByRefreshToken(refreshToken)
                    .filter(user1 -> user1.getId().equals(id))
                    .ifPresentOrElse(user1 -> {
                        jwtTokenService.sendAccessAndRefreshToken(
                                response, jwtTokenService.createToken(user1, Duration.ofMinutes(30)), refreshToken);
                    }, () -> {throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);});
        }
    }
}
