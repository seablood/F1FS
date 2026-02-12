package kr.co.F1FS.app.global.application.service;

import kr.co.F1FS.app.domain.suspend.application.mapper.SuspensionLogMapper;
import kr.co.F1FS.app.domain.suspend.application.port.in.DeleteSuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.application.port.in.QuerySuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.global.application.port.in.CheckAccountStatusUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.global.util.exception.authentication.AccountDormantException;
import kr.co.F1FS.app.global.util.exception.authentication.AccountLockedException;
import kr.co.F1FS.app.global.util.exception.authentication.AccountSuspendException;
import kr.co.F1FS.app.global.util.exception.authentication.AccountTemporaryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckAccountStatusService implements CheckAccountStatusUseCase {
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteSuspensionLogUseCase deleteSuspensionLogUseCase;
    private final QuerySuspensionLogUseCase querySuspensionLogUseCase;
    private final SuspensionLogMapper suspensionLogMapper;

    @Override
    public void checkAccount(PrincipalDetails principalDetails) {
        if(!principalDetails.isAccountNonExpired()){
            throw new AccountDormantException("휴면으로 전환된 계정입니다. 인증 절차를 진행해주세요.");
        }

        if(!principalDetails.isEnabled()){
            throw new AccountTemporaryException("계정 인증이 완료되지 않았습니다. 인증 절차를 완료해주세요.");
        }

        if(!principalDetails.isAccountNonLocked()){
            throw new AccountLockedException("로그인 시도 횟수 초과로 계정이 잠겨 있습니다. 5분 후 다시 시도해주세요.");
        }

        if(principalDetails.getUser().getRole().equals(Role.DISCIPLINE)){
            SuspensionLog log = querySuspensionLogUseCase.findBySuspendUser(principalDetails.getUser());

            if(principalDetails.getUser().isSuspendUntil()) {
                throw new AccountSuspendException("이용이 정지된 계정입니다.", suspensionLogMapper.toResponseSuspensionLogDTO(log));
            }
            else {
                updateUserUseCase.updateRole(principalDetails.getUser(), Role.USER);
                deleteSuspensionLogUseCase.delete(log);
            }
        }
    }
}
