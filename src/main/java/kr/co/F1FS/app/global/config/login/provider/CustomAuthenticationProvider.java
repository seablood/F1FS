package kr.co.F1FS.app.global.config.login.provider;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.suspend.application.mapper.SuspensionLogMapper;
import kr.co.F1FS.app.domain.suspend.application.port.in.DeleteSuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.application.port.in.QuerySuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.config.auth.PrincipalDetailsService;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.global.util.exception.authentication.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final DeleteSuspensionLogUseCase deleteSuspensionLogUseCase;
    private final QuerySuspensionLogUseCase querySuspensionLogUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PrincipalDetailsService detailsService;
    private final SuspensionLogMapper suspensionLogMapper;


    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        PrincipalDetails principalDetails = (PrincipalDetails) detailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(rawPassword, principalDetails.getPassword())){
            throw new AccountPasswordException("패스워드가 일치하지 않습니다. 다시 로그인해주세요.");
        }

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

        return new UsernamePasswordAuthenticationToken(principalDetails.getUser(), null,
                principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
