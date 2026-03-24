package kr.co.F1FS.app.domain.follow.application.port.out.driver;

import kr.co.F1FS.app.domain.follow.domain.FollowDriver;

import java.util.List;

public interface FollowDriverJpaPort {
    FollowDriver save(FollowDriver followDriver);
    FollowDriver findByUserAndDriver(Long userId, Long driverId);
    List<FollowDriver> findAllByUser(Long userId);
    boolean existsByUserAndDriver(Long userId, Long driverId);
    void delete(FollowDriver followDriver);
}
