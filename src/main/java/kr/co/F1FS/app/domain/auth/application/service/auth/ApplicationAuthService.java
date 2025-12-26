package kr.co.F1FS.app.domain.auth.application.service.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.auth.application.mapper.AuthMapper;
import kr.co.F1FS.app.domain.auth.application.port.in.auth.AuthUseCase;
import kr.co.F1FS.app.domain.auth.application.port.in.BlackListUseCase;
import kr.co.F1FS.app.domain.auth.application.port.in.verification.CreateVerificationUseCase;
import kr.co.F1FS.app.domain.auth.presentation.dto.AuthorizationUserDTO;
import kr.co.F1FS.app.domain.email.application.port.in.EmailUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.CreateUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.DeleteUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.global.util.CookieUtil;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationAuthService implements AuthUseCase {
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final CreateVerificationUseCase createVerificationUseCase;
    private final EmailUseCase emailUseCase;
    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BlackListUseCase blackListUseCase;
    private final JwtTokenService jwtTokenService;

    @Override
    @Transactional
    public ResponseUserDTO save(CreateUserDTO userDTO){
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        ResponseUserDTO dto = createUserUseCase.createUser(authMapper.toCreateUserCommand(userDTO));
        sendEmail(dto);

        return dto;
    }

    @Override
    public void sendEmail(User user, String option){
        VerificationCode code = createVerificationUseCase.createCode(user);
        emailUseCase.sendAuthEmail(user, code.getCode(), option);
    }

    @Override
    public void sendEmail(ResponseUserDTO dto) {
        VerificationCode code = createVerificationUseCase.createCode(dto);
        emailUseCase.sendCreateAccountEmail(dto, code.getCode());
    }

    @Override
    public void sendEmail(AuthorizationUserDTO dto, String option){
        User user = queryUserUseCase.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        VerificationCode code = createVerificationUseCase.createCode(user);
        emailUseCase.sendAuthEmail(user, code.getCode(), option);
    }

    @Override
    @Transactional
    public void updatePassword(User user, ModifyPasswordDTO passwordDTO){
        updateUserUseCase.updatePassword(user, bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
    }

    @Override
    @Transactional
    public void logout(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                       User user) {
        try {
            setBlackList(accessToken);
            setBlackList(refreshToken);
            CookieUtil.deleteCookie(request, response, "refresh_token");
            updateUserUseCase.updateRefreshToken(user, "");
        } catch (Exception e) {
            throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);
        }
    }

    @Override
    @Transactional
    public void secession(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                          User user){
        try {
            setBlackList(accessToken);
            setBlackList(refreshToken);
            CookieUtil.deleteCookie(request, response, "refresh_token");
            deleteUserUseCase.delete(user);
        } catch (Exception e) {
            throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);
        }
    }

    @Override
    public void setBlackList(String token){
        Claims claims = jwtTokenService.getClaims(token);

        Date expiration = claims.getExpiration();
        long expirationMillis = expiration.getTime() - System.currentTimeMillis();

        // 만료 시간이 남아 있는 경우 블랙리스트 추가
        if (expirationMillis > 0) {
            blackListUseCase.addBlackList(token, expirationMillis);
        }
    }
}
