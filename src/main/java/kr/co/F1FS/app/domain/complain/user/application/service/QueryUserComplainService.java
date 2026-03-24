package kr.co.F1FS.app.domain.complain.user.application.service;

import kr.co.F1FS.app.domain.complain.user.application.mapper.UserComplainMapper;
import kr.co.F1FS.app.domain.complain.user.application.port.in.QueryUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.ResponseUserComplainListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.user.ResponseUserComplainDTO;
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
    public Page<ResponseUserComplainListDTO> findUserComplainListForDTO(Pageable pageable) {
        return userComplainJpaPort.findUserComplainList(pageable);
    }

    @Override
    public UserComplain findByIdWithJoin(Long id) {
        return userComplainJpaPort.findByIdWithJoin(id);
    }

    @Override
    public ResponseUserComplainDTO findByIdForDTO(Long id) {
        return userComplainMapper.toResponseUserComplainDTO(userComplainJpaPort.findByIdWithJoin(id));
    }

    @Override
    public List<UserComplain> findAllByToUser(User toUser) {
        return userComplainJpaPort.findAllByToUser(toUser);
    }

    @Override
    public Page<ResponseUserComplainListDTO> findAllByToUserForDTO(Long userId, Pageable pageable) {
        return userComplainJpaPort.findAllByToUser(userId, pageable);
    }

    @Override
    public Page<ResponseUserComplainListDTO> findAllByFromUserForDTO(Long userId, Pageable pageable) {
        return userComplainJpaPort.findAllByFromUser(userId, pageable);
    }
}
