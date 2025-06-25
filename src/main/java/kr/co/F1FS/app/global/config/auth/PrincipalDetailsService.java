package kr.co.F1FS.app.global.config.auth;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.infrastructure.repository.UserRepository;
import kr.co.F1FS.app.global.config.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RedisHandler redisHandler;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElse(null);
        if(!(user == null)) return new PrincipalDetails(user, redisHandler);
        return null;
    }
}
