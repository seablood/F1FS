package kr.co.F1FS.app.domain.follow.infrastructure.adapter.driver;

import kr.co.F1FS.app.domain.follow.application.port.out.driver.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowDriverRepository;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl.FollowDriverDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowDriverJpaAdapter implements FollowDriverJpaPort {
    private final FollowDriverRepository followDriverRepository;
    private final FollowDriverDSLRepository followDriverDSLRepository;

    @Override
    public FollowDriver save(FollowDriver followDriver) {
        return followDriverRepository.save(followDriver);
    }

    @Override
    public FollowDriver findByUserAndDriver(Long userId, Long driverId) {
        return followDriverDSLRepository.findByUserAndDriver(userId, driverId);
    }

    @Override
    public List<FollowDriver> findAllByUser(Long userId) {
        return followDriverDSLRepository.findAllByUser(userId);
    }

    @Override
    public boolean existsByUserAndDriver(Long userId, Long driverId) {
        return followDriverDSLRepository.existsByUserAndDriver(userId, driverId);
    }

    @Override
    public void delete(FollowDriver followDriver) {
        followDriverRepository.delete(followDriver);
    }
}
