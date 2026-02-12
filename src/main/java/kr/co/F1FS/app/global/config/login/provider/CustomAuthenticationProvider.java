package kr.co.F1FS.app.global.config.login.provider;

import kr.co.F1FS.app.global.application.port.in.CheckAccountStatusUseCase;
import kr.co.F1FS.app.global.config.auth.PrincipalDetails;
import kr.co.F1FS.app.global.config.auth.PrincipalDetailsService;
import kr.co.F1FS.app.global.util.exception.authentication.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CheckAccountStatusUseCase checkAccountStatusUseCase;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PrincipalDetailsService detailsService;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        PrincipalDetails principalDetails = (PrincipalDetails) detailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(rawPassword, principalDetails.getPassword())){
            throw new AccountPasswordException("패스워드가 일치하지 않습니다. 다시 로그인해주세요.");
        }
        checkAccountStatusUseCase.checkAccount(principalDetails);

        return new UsernamePasswordAuthenticationToken(principalDetails.getUser(), null,
                principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
