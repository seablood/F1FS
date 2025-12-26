package kr.co.F1FS.app.domain.complain.user.application.service;

import kr.co.F1FS.app.domain.complain.user.application.mapper.UserComplainMapper;
import kr.co.F1FS.app.domain.complain.user.application.port.in.QueryUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponseUserComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserComplainService implements QueryUserComplainUseCase {
    private final UserComplainJpaPort userComplainJpaPort;
    private final UserComplainMapper userComplainMapper;

    @Override
    public Page<UserComplain> findAll(Pageable pageable) {
        return userComplainJpaPort.findAll(pageable);
    }

    @Override
    public UserComplain findById(Long id) {
        return userComplainJpaPort.findById(id);
    }

    @Override
    public ResponseUserComplainDTO findByIdForDTO(Long id) {
        return userComplainMapper.toResponseUserComplainDTO(userComplainJpaPort.findById(id));
    }

    @Override
    public List<UserComplain> findAllByToUser(User toUser) {
        return userComplainJpaPort.findAllByToUser(toUser);
    }

    @Override
    public Page<UserComplain> findAllByToUser(User toUser, Pageable pageable) {
        return userComplainJpaPort.findAllByToUser(toUser, pageable);
    }

    @Override
    public Page<ResponseUserComplainDTO> findAllByFromUserForDTO(User fromUser, Pageable pageable) {
        return userComplainJpaPort.findAllByFromUser(fromUser, pageable)
                .map(userComplain -> userComplainMapper.toResponseUserComplainDTO(userComplain));
    }

    @Override
    public Page<ResponseUserComplainDTO> findAllByToUserForDTO(User toUser, Pageable pageable) {
        return userComplainJpaPort.findAllByToUser(toUser, pageable)
                .map(userComplain -> userComplainMapper.toResponseUserComplainDTO(userComplain));
    }
}
