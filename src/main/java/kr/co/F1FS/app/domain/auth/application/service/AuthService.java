package kr.co.F1FS.app.domain.auth.application.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.auth.application.mapper.AuthMapper;
import kr.co.F1FS.app.domain.auth.application.port.in.AuthUseCase;
import kr.co.F1FS.app.domain.auth.application.port.out.AuthUserPort;
import kr.co.F1FS.app.domain.auth.presentation.dto.AuthorizationUserDTO;
import kr.co.F1FS.app.domain.email.application.port.in.EmailUseCase;
import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.infrastructure.repository.VerificationCodeRepository;
import kr.co.F1FS.app.global.util.CookieUtil;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements AuthUseCase {
    private final UserUseCase userUseCase;
    private final EmailUseCase emailUseCase;
    private final AuthUserPort authUserPort;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final VerificationCodeRepository verificationCodeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationService validationService;
    private final BlackListService blackListService;
    private final JwtTokenService jwtTokenService;
    private final CacheEvictUtil cacheEvictUtil;

    @Transactional
    public ResponseUserDTO save(CreateUserDTO userDTO){
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.toUser(authMapper.toCreateUserCommand(userDTO));
        validationService.checkValid(user);
        userUseCase.updateLastLoginDate(user);

        authUserPort.save(user);
        sendEmail(user, "create_account");

        return userMapper.toResponseUserDTO(user);
    }

    public void sendEmail(User user, String option){
        VerificationCode code = createVerificationCode(user);
        emailUseCase.sendAuthEmail(user, code.getCode(), option);
    }

    public void sendEmail(AuthorizationUserDTO dto, String option){
        User user = authUserPort.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        VerificationCode code = createVerificationCode(user);
        emailUseCase.sendAuthEmail(user, code.getCode(), option);
    }

    public VerificationCode createVerificationCode(User user){
        String code = generateVerificationCode();
        VerificationCode verificationCode = VerificationCode.builder()
                .email(user.getEmail())
                .code(code)
                .build();

        return verificationCodeRepository.save(verificationCode);
    }

    public String generateVerificationCode(){
        String base56Character = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for(int i = 0; i<6; i++){
            int idx = random.nextInt(base56Character.length());
            sb.append(base56Character.charAt(idx));
        }

        return sb.toString();
    }

    @Transactional
    public void verifyCode(String email, String code){
        VerificationCode verificationCode = verificationCodeRepository.findVerificationCodeByEmailAndCode(email, code)
                .orElseThrow(() -> new IllegalArgumentException("인증 오류"));

        if(verificationCode.isExpired()){
            throw new IllegalArgumentException("인증 만료");
        } else {
            User user = authUserPort.findUserByEmail(verificationCode.getEmail());
            userUseCase.updateRole(user, Role.USER);
            authUserPort.saveAndFlush(user);
        }

        verificationCodeRepository.delete(verificationCode);
        log.info("인증 완료 및 인증 코드 삭제 : {}", email);
    }

    @Transactional
    public void updatePassword(User user, ModifyPasswordDTO passwordDTO){
        cacheEvictUtil.evictCachingUser(user);
        userUseCase.updatePassword(user, bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
        authUserPort.saveAndFlush(user);
    }

    @Transactional
    public void logout(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                       User user) {
        try {
            setBlackList(accessToken);
            setBlackList(refreshToken);
            CookieUtil.deleteCookie(request, response, "refresh_token");
            userUseCase.updateRefreshToken(user, "");
            authUserPort.saveAndFlush(user);
        } catch (Exception e) {
            throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);
        }
    }

    @Transactional
    public void secession(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                          User user){
        try {
            setBlackList(accessToken);
            setBlackList(refreshToken);
            CookieUtil.deleteCookie(request, response, "refresh_token");
            cacheEvictUtil.evictCachingUser(user);
            authUserPort.delete(user);
        } catch (Exception e) {
            throw new UserException(UserExceptionType.USER_AUTHENTICATION_ERROR);
        }
    }

    public void setBlackList(String token){
        Claims claims = jwtTokenService.getClaims(token);

        Date expiration = claims.getExpiration();
        long expirationMillis = expiration.getTime() - System.currentTimeMillis();

        // 만료 시간이 남아 있는 경우 블랙리스트 추가
        if (expirationMillis > 0) {
            blackListService.addBlackList(token, expirationMillis);
        }
    }
}
