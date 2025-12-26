package kr.co.F1FS.app.domain.complain.user.application.service;

import kr.co.F1FS.app.domain.complain.user.application.mapper.UserComplainMapper;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserComplainDomainService {
    private final UserComplainMapper userComplainMapper;

    public UserComplain createEntity(CreateUserComplainDTO dto, User toUser, User user){
        return userComplainMapper.toUserComplain(dto, toUser, user);
    }
}
