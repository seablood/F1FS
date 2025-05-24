package kr.co.F1FS.app.application.admin.auth;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.ValidationService;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.auth.VerificationCodeRepository;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.presentation.admin.auth.dto.CreateAdminUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAuthService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    @Transactional
    public User save(CreateAdminUserDTO dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User newAdminUser = CreateAdminUserDTO.toEntity(dto);
        validationService.checkValid(newAdminUser);
        newAdminUser.updateLastLoginDate();

        newAdminUser.updateRole(Role.ADMIN);
        log.info("관리자 계정 전환(생성) 완료 : {}", newAdminUser.getUsername());

        return userRepository.save(newAdminUser);
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

        List<User> list = userRepository.findAllByLastLoginDateBeforeOrLastLoginDateIsNull(sixMonthAgo)
                .stream().filter(user -> user.getRole().equals(Role.USER)).toList();

        for (User user : list){
            user.updateRole(Role.DORMANT);
        }

        userRepository.saveAllAndFlush(list);

        log.info("휴면 전환 완료: {}명", list.size());
    }
}
