package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateUserComplainUseCase {
    UserComplain save(CreateUserComplainDTO dto, User toUser, User user);
}
