package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.driver.QueryFollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.driver.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.follow.presentation.dto.driver.ResponseFollowDriverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFollowDriverService implements QueryFollowDriverUseCase {
    private final FollowDriverJpaPort followDriverJpaPort;
    private final FollowMapper followMapper;

    @Override
    public FollowDriver findByUserAndDriver(Long userId, Long driverId) {
        return followDriverJpaPort.findByUserAndDriver(userId, driverId);
    }

    @Override
    public List<ResponseFollowDriverDTO> findAllByUserForDTO(Long userId) {
        return followDriverJpaPort.findAllByUser(userId).stream()
                .map(followDriver -> followDriver.getFolloweeDriver())
                .map(followeeDriver -> followMapper.toResponseFollowDriverDTO(followeeDriver))
                .toList();
    }
}
