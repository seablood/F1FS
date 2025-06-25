package kr.co.F1FS.app.domain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.application.port.out.AuthUserPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.infrastructure.repository.UserRepository;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUserAdapter implements AuthUserPort {
    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveAndFlush(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }
}
