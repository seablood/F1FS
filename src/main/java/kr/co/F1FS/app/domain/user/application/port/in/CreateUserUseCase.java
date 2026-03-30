package kr.co.F1FS.app.domain.user.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateAdminUserCommand;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import kr.co.F1FS.app.global.config.oauth2.provider.OAuth2UserInfo;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

public interface CreateUserUseCase {
    User createUser(CreateUserCommand command);
    User createEntity(OAuth2UserInfo userInfo);
    ResponseUserDTO createAdminUser(CreateAdminUserCommand command);
}
