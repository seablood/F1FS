package kr.co.F1FS.app.domain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.user.application.port.out.AdminUserPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserAdapter implements AdminUserPort {
    private final UserRepository userRepository;

    @Override
    public void saveAndFlush(User user) {
        userRepository.saveAndFlush(user);
    }
}
