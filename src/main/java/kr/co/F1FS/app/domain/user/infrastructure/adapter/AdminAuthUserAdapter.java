package kr.co.F1FS.app.domain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.auth.application.port.out.AdminAuthUserPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminAuthUserAdapter implements AdminAuthUserPort {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void saveAllAndFlush(List<User> list) {
        userRepository.saveAllAndFlush(list);
    }

    @Override
    public List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo) {
        return userRepository.findAllByLastLoginDateBeforeOrLastLoginDateIsNull(sixMonthAgo);
    }
}
