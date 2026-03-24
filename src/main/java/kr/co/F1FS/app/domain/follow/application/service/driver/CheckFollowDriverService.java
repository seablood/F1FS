package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.follow.application.port.in.driver.CheckFollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.driver.FollowDriverJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckFollowDriverService implements CheckFollowDriverUseCase {
    private final FollowDriverJpaPort followDriverJpaPort;

    @Override
    public boolean existsByUserAndDriver(Long userId, Long driverId) {
        return followDriverJpaPort.existsByUserAndDriver(userId, driverId);
    }
}
