package kr.co.F1FS.app.domain.complain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.infrastructure.repository.UserComplainRepository;
import kr.co.F1FS.app.domain.complain.user.infrastructure.repository.dsl.UserComplainDSLRepository;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.ResponseUserComplainListDTO;
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
    private final UserComplainDSLRepository userComplainDSLRepository;

    @Override
    public UserComplain save(UserComplain userComplain) {
        return userComplainRepository.save(userComplain);
    }

    @Override
    public Page<ResponseUserComplainListDTO> findUserComplainList(Pageable pageable) {
        return userComplainDSLRepository.findUserComplainList(pageable);
    }

    @Override
    public UserComplain findByIdWithJoin(Long id) {
        return userComplainDSLRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_COMPLAIN_NOT_FOUND));
    }

    @Override
    public Page<ResponseUserComplainListDTO> findAllByToUser(Long userId, Pageable pageable) {
        return userComplainDSLRepository.findAllByToUser(userId, pageable);
    }

    @Override
    public Page<ResponseUserComplainListDTO> findAllByFromUser(Long userId, Pageable pageable) {
        return userComplainDSLRepository.findAllByFromUser(userId, pageable);
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
