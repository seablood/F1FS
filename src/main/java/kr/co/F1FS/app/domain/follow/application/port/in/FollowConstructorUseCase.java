package kr.co.F1FS.app.domain.follow.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FollowConstructorUseCase {
    void toggle(User user, Long id);
    List<ResponseFollowConstructorDTO> getFollowingConstructor(User user);
    boolean isFollowed(User user, Constructor constructor);
}
