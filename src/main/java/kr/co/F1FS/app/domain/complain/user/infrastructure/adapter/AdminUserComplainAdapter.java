package kr.co.F1FS.app.domain.complain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.user.application.port.out.AdminUserComplainPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.infrastructure.repository.UserComplainRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminUserComplainAdapter implements AdminUserComplainPort {
    private final UserComplainRepository userComplainRepository;

    @Override
    public Page<UserComplain> getComplainByUser(User toUser, Pageable pageable) {
        return userComplainRepository.findAllByToUser(toUser, pageable);
    }

    @Override
    public List<UserComplain> getComplainByUser(User toUser) {
        return userComplainRepository.findAllByToUser(toUser);
    }

    @Override
    public void delete(UserComplain userComplain) {
        userComplainRepository.delete(userComplain);
    }
}
