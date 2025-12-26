package kr.co.F1FS.app.domain.follow.application.port.in.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface QueryFollowConstructorUseCase {
    FollowConstructor findByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
    List<ResponseFollowConstructorDTO> findByFollowerUserForDTO(User user);
}
