package kr.co.F1FS.app.domain.complain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.infrastructure.repository.UserComplainRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserComplainJpaAdapter implements UserComplainJpaPort {
    private final UserComplainRepository userComplainRepository;

    @Override
    public UserComplain save(UserComplain userComplain) {
        return userComplainRepository.save(userComplain);
    }

    @Override
    public Page<UserComplain> findAll(Pageable pageable) {
        return userComplainRepository.findAll(pageable);
    }

    @Override
    public UserComplain findById(Long id) {
        return userComplainRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_COMPLAIN_NOT_FOUND));
    }

    @Override
    public Page<UserComplain> findAllByToUser(User toUser, Pageable pageable) {
        return userComplainRepository.findAllByToUser(toUser, pageable);
    }

    @Override
    public Page<UserComplain> findAllByFromUser(User fromUser, Pageable pageable) {
        return userComplainRepository.findAllByFromUser(fromUser, pageable);
    }

    @Override
    public List<UserComplain> findAllByToUser(User toUser) {
        return userComplainRepository.findAllByToUser(toUser);
    }

    @Override
    public void delete(UserComplain userComplain) {
        userComplainRepository.delete(userComplain);
    }
}
