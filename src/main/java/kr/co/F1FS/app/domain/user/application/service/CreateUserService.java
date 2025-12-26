package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.CreateUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.out.UserJpaPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.config.oauth2.provider.OAuth2UserInfo;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {
    private final UserJpaPort userJpaPort;
    private final UserDomainService userDomainService;
    private final ValidationService validationService;
    private final UserMapper userMapper;

    @Override
    public ResponseUserDTO createUser(CreateUserCommand command) {
        User user = userDomainService.createEntity(command);
        validationService.checkValid(user);
        userDomainService.updateLastLoginDate(user);

        return userMapper.toResponseUserDTO(userJpaPort.save(user));
    }

    @Override
    public User createEntity(OAuth2UserInfo userInfo) {
        User user = userDomainService.createEntity(userInfo);
        validationService.checkValid(user);
        userDomainService.updateRole(user, Role.USER);

        return userJpaPort.save(user);
    }

    @Override
    public ResponseUserDTO createAdminUser(CreateAdminUserDTO dto) {
        User user = userDomainService.createEntity(dto);
        validationService.checkValid(user);
        userDomainService.updateLastLoginDate(user);
        userDomainService.updateRole(user, Role.ADMIN);

        return userMapper.toResponseUserDTO(userJpaPort.save(user));
    }
}
