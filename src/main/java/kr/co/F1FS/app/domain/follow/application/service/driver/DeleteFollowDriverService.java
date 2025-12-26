package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.follow.application.port.in.driver.DeleteFollowDriverUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.driver.FollowDriverJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFollowDriverService implements DeleteFollowDriverUseCase {
    private final FollowDriverJpaPort followDriverJpaPort;

    @Override
    public void delete(FollowDriver followDriver) {
        followDriverJpaPort.delete(followDriver);
    }
}
