package kr.co.F1FS.app.domain.auth.application.service.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.auth.application.mapper.AuthMapper;
import kr.co.F1FS.app.domain.auth.application.port.in.blackList.AddBlackListUseCase;
import kr.co.F1FS.app.domain.auth.application.port.in.auth.AuthUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.user.CreateUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.user.DeleteUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.user.QueryUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.email.application.port.in.EmailUseCase;
import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.CreateUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.DeleteUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.domain.user.domain.User;
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
    private final CreateUserSearchUseCase createUserSearchUseCase;
    private final DeleteUserSearchUseCase deleteUserSearchUseCase;
    private final QueryUserSearchUseCase queryUserSearchUseCase;
    private final AddBlackListUseCase addBlackListUseCase;
    private final EmailUseCase emailUseCase;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    @Transactional
    public ResponseUserDTO save(CreateUserDTO userDTO){
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User user = createUserUseCase.createUser(authMapper.toCreateUserCommand(userDTO));
        emailUseCase.sendCreateAccountEmail(user.getEmail(), user.getNickname());

        createUserSearchUseCase.save(user);

        return userMapper.toResponseUserDTO(user);
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

            UserDocument userDocument = queryUserSearchUseCase.findById(user.getId());
            deleteUserSearchUseCase.delete(userDocument);
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
            addBlackListUseCase.addBlackList(token, expirationMillis);
        }
    }
}
