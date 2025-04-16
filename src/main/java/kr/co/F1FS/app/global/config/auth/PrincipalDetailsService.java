package kr.co.F1FS.app.global.config.auth;

import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElse(null);
        if(!(user == null)) return new PrincipalDetails(user);
        return null;
    }
}
