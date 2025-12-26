package kr.co.F1FS.app.domain.follow.application.port.in.constructor;

import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FollowConstructorUseCase {
    void toggle(User user, Long id);
    List<ResponseFollowConstructorDTO> getFollowingConstructor(User user);
    List<ResponseFollowConstructorDTO> getFollowingConstructorByNickname(String nickname);
}
