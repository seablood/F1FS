package kr.co.F1FS.app.domain.admin.auth.application.service;

import kr.co.F1FS.app.domain.admin.auth.application.port.in.AdminAuthUseCase;
import kr.co.F1FS.app.domain.admin.auth.application.port.out.AdminAuthUserPort;
import kr.co.F1FS.app.domain.admin.auth.application.port.out.AdminAuthVerificationCodePort;
import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.domain.admin.auth.presentation.dto.CreateAdminUserDTO;
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
public class AdminAuthService implements AdminAuthUseCase {
    private final UserUseCase userUseCase;
    private final AdminAuthUserPort userPort;
    private final AdminAuthVerificationCodePort verificationCodePort;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    @Transactional
    public ResponseUserDTO save(CreateAdminUserDTO dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User newAdminUser = userMapper.toUser(dto);
        validationService.checkValid(newAdminUser);
        userUseCase.updateLastLoginDate(newAdminUser);

        userUseCase.updateRole(newAdminUser, Role.ADMIN);
        log.info("관리자 계정 전환(생성) 완료 : {}", newAdminUser.getUsername());

        userPort.save(newAdminUser);
        return userMapper.toResponseUserDTO(newAdminUser);
    }

    // 5분에 한번씩 만료된 인증 코드 삭제
    @Transactional
    @Scheduled(fixedRate = 300000)
    public void deleteVerificationCode(){
        log.info("만료 인증코드 삭제");

        verificationCodePort.findAll().stream()
                .filter(verificationCode -> verificationCode.isExpired())
                .forEach(verificationCode -> verificationCodePort.delete(verificationCode));
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void markDormantAccounts(){
        log.info("6개월 이상 비로그인 자 휴면 계정 전환");

        LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);

        List<User> list = userPort.findAllByLastLoginDateBeforeOrLastLoginDateIsNull(sixMonthAgo)
                .stream().filter(user -> user.getRole().equals(Role.USER)).toList();

        for (User user : list){
            userUseCase.updateRole(user, Role.DORMANT);
        }

        userPort.saveAllAndFlush(list);

        log.info("휴면 전환 완료: {}명", list.size());
    }
}
