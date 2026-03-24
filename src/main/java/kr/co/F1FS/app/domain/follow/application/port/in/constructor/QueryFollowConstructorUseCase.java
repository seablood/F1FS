package kr.co.F1FS.app.domain.follow.application.port.in.constructor;

import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;

import java.util.List;

public interface QueryFollowConstructorUseCase {
    FollowConstructor findByUserAndConstructor(Long userId, Long constructorId);
    List<ResponseFollowConstructorDTO> findAllByUserForDTO(Long userId);
}
