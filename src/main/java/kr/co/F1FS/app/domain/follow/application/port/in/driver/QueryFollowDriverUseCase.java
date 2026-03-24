package kr.co.F1FS.app.domain.follow.application.port.in.driver;

import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;

import java.util.List;

public interface QueryFollowDriverUseCase {
    FollowDriver findByUserAndDriver(Long userId, Long driverId);
    List<ResponseFollowDriverDTO> findAllByUserForDTO(Long userId);
}
