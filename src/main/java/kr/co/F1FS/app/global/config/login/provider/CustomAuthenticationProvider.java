package kr.co.F1FS.app.global.config.login.provider;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.suspend.SuspensionLogService;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.config.auth.PrincipalDetailsService;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.global.util.exception.authentication.AccountPasswordException;
import kr.co.F1FS.app.global.util.exception.authentication.AccountSuspendException;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import kr.co.F1FS.app.presentation.suspend.dto.ResponseSuspensionLogDTO;
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
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PrincipalDetailsService detailsService;
    private final SuspensionLogService logService;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
        PrincipalDetails principalDetails = (PrincipalDetails) detailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new AccountPasswordException("패스워드가 일치하지 않습니다. 다시 로그인해주세요.");
        }

        if(user.getRole().equals(Role.DISCIPLINE)){
            if(user.isSuspendUntil()) {
                ResponseSuspensionLogDTO dto = logService.getSuspensionLog(user);
                throw new AccountSuspendException("이용이 정지된 계정입니다.", dto);
            }
            else {
                user.updateRole(Role.USER);
                userRepository.saveAndFlush(user);
            }
        }

        return new UsernamePasswordAuthenticationToken(user, null,
                principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
