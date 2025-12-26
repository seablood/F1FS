package kr.co.F1FS.app.domain.complain.user.application.service;

import kr.co.F1FS.app.domain.complain.user.application.port.in.CreateUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserComplainService implements CreateUserComplainUseCase {
    private final UserComplainJpaPort userComplainJpaPort;
    private final UserComplainDomainService userComplainDomainService;

    @Override
    public UserComplain save(CreateUserComplainDTO dto, User toUser, User user) {
        UserComplain userComplain = userComplainDomainService.createEntity(dto, toUser, user);

        return userComplainJpaPort.save(userComplain);
    }
}
