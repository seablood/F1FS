package kr.co.F1FS.app.domain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.auth.application.port.out.TokenUserPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenUserAdapter implements TokenUserPort {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }
}
