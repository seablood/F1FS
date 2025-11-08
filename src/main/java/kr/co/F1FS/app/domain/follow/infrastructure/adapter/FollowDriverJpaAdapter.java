package kr.co.F1FS.app.domain.follow.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowDriverRepository;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowDriverDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowDriverJpaAdapter implements FollowDriverJpaPort {
    private final FollowDriverRepository followDriverRepository;
    private final FollowMapper followMapper;

    @Override
    public FollowDriver save(FollowDriver followDriver) {
        return followDriverRepository.save(followDriver);
    }

    @Override
    public FollowDriver findByFollowerUserAndFolloweeDriver(User user, Driver driver) {
        return followDriverRepository.findByFollowerUserAndFolloweeDriver(user, driver);
    }

    @Override
    public List<ResponseFollowDriverDTO> findByFollowerUser(User user) {
        return followDriverRepository.findByFollowerUser(user).stream()
                .map(followDriver -> followDriver.getFolloweeDriver())
                .map(followeeDriver -> followMapper.toResponseFollowDriverDTO(followeeDriver))
                .toList();
    }

    @Override
    public boolean existsFollowDriverByFollowerUserAndFolloweeDriver(User user, Driver driver) {
        return followDriverRepository.existsFollowDriverByFollowerUserAndFolloweeDriver(user, driver);
    }

    @Override
    public void delete(FollowDriver followDriver) {
        followDriverRepository.delete(followDriver);
    }
}
