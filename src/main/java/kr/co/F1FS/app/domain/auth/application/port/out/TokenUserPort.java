package kr.co.F1FS.app.domain.auth.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

import java.util.Optional;

public interface TokenUserPort {
    Optional<User> findByRefreshToken(String refreshToken);
}
