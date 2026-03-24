package kr.co.F1FS.app.domain.follow.application.port.in.driver;

public interface CheckFollowDriverUseCase {
    boolean existsByUserAndDriver(Long userId, Long driverId);
}
