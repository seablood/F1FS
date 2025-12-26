package kr.co.F1FS.app.domain.auth.application.service.auth.admin;

import kr.co.F1FS.app.domain.auth.application.port.in.auth.admin.AdminAuthUseCase;
import kr.co.F1FS.app.domain.auth.application.port.in.verification.CheckVerificationUseCase;
import kr.co.F1FS.app.domain.auth.application.port.in.verification.QueryVerificationUseCase;
import kr.co.F1FS.app.domain.auth.application.port.in.verification.VerifyAndDeleteVerificationUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.CreateUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateAdminUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationAdminAuthService implements AdminAuthUseCase {
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final CheckVerificationUseCase checkVerificationUseCase;
    private final QueryVerificationUseCase queryVerificationUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final VerifyAndDeleteVerificationUseCase verifyAndDeleteVerificationUseCase;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseUserDTO save(CreateAdminUserDTO dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        ResponseUserDTO userDTO = createUserUseCase.createAdminUser(dto);

        log.info("관리자 계정 전환(생성) 완료 : {}", userDTO.getUsername());

        return userDTO;
    }

    // 5분에 한번씩 만료된 인증 코드 삭제
    @Transactional
    @Scheduled(fixedRate = 300000)
    public void deleteVerificationCode(){
        log.info("만료 인증코드 삭제");

        queryVerificationUseCase.findAll().stream()
                .filter(verificationCode -> checkVerificationUseCase.isExpired(verificationCode))
                .forEach(verificationCode -> verifyAndDeleteVerificationUseCase.delete(verificationCode));
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void markDormantAccounts(){
        log.info("6개월 이상 비로그인 자 휴면 계정 전환");

        LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);

        List<User> list = queryUserUseCase.findAllByLastLoginDateBeforeOrLastLoginDateIsNull(sixMonthAgo);

        for (User user : list){
            updateUserUseCase.updateRole(user, Role.DORMANT);
        }

        log.info("휴면 전환 완료: {}명", list.size());
    }
}
