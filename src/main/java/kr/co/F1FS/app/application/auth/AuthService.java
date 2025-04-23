package kr.co.F1FS.app.application.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.BlackListService;
import kr.co.F1FS.app.global.config.jwt.service.JwtTokenService;
import kr.co.F1FS.app.presentation.user.dto.AuthorizationUserDTO;
import kr.co.F1FS.app.presentation.user.dto.CreateUserDTO;
import kr.co.F1FS.app.presentation.user.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.model.rdb.VerificationCode;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.domain.repository.rdb.auth.VerificationCodeRepository;
import kr.co.F1FS.app.application.email.EmailService;
import kr.co.F1FS.app.global.util.CookieUtil;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.application.ValidationService;
import kr.co.F1FS.app.global.util.exception.email.EmailException;
import kr.co.F1FS.app.global.util.exception.email.EmailExceptionType;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationService validationService;
    private final EmailService emailService;
    private final BlackListService blackListService;
    private final JwtTokenService jwtTokenService;

    @Transactional
    public User save(CreateUserDTO userDTO){
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User user = CreateUserDTO.toEntity(userDTO);
        validationService.checkValid(user);
        user.updateLastLoginDate();

        userRepository.save(user);
        sendEmail(user, "create_account");

        return user;
    }

    public void sendEmail(User user, String option){
        VerificationCode code = createVerificationCode(user);
        try {
            switch (option){
                case "create_account" :
                    emailService.sendCreateAccountEmail(user, code.getCode());
                    break;
                case "update_password" :
                    emailService.sendUpdatePasswordEmail(user, code.getCode());
                    break;
                case "active_account" :
                    emailService.sendActiveAccountEmail(user, code.getCode());
                    break;
                default:
                    throw new RuntimeException("이메일 형식 오류");
            }
        } catch (Exception e){
            throw new EmailException(EmailExceptionType.SEND_EMAIL_ERROR);
        }
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
            User user = userRepository.findUserByEmail(verificationCode.getEmail())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
            user.updateRole(Role.USER);
            userRepository.saveAndFlush(user);
        }

        verificationCodeRepository.delete(verificationCode);
    }

    @Transactional
    public void updatePassword(User user, ModifyPasswordDTO passwordDTO){
        user.updatePassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void logout(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                       User user) {
        try {
            setBlackList(accessToken);
            setBlackList(refreshToken);
            CookieUtil.deleteCookie(request, response, "refresh_token");
            user.updateRefreshToken("");
            userRepository.saveAndFlush(user);
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

    public User authenticationUser(AuthorizationUserDTO dto){
        return userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }

    // 5분에 한번씩 만료된 인증 코드 삭제
    @Transactional
    @Scheduled(fixedRate = 300000)
    public void deleteVerificationCode(){
        log.info("만료 인증코드 삭제");

        verificationCodeRepository.findAll().stream()
                .filter(verificationCode -> verificationCode.isExpired())
                .forEach(verificationCode -> verificationCodeRepository.delete(verificationCode));
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void markDormantAccounts(){
        log.info("6개월 이상 비로그인 자 휴면 계정 전환");

        LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);

        List<User> list = userRepository.findAllByLastLoginDateBeforeOrLastLoginDateIsNull(sixMonthAgo);

        for (User user : list){
            user.updateRole(Role.DORMANT);
        }

        userRepository.saveAllAndFlush(list);

        log.info("휴면 전환 완료: {}명", list.size());
    }
}
