package kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.follow.domain.FollowDriver;

import java.util.List;

public interface FollowDriverDSLRepository {
    FollowDriver findByUserAndDriver(Long userId, Long driverId);
    List<FollowDriver> findAllByUser(Long userId);
    boolean existsByUserAndDriver(Long userId, Long driverId);
}
