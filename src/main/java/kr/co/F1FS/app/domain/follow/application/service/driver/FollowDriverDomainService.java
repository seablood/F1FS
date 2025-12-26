package kr.co.F1FS.app.domain.follow.application.service.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowDriverDomainService {
    private final FollowMapper followMapper;

    public FollowDriver createEntity(User user, Driver driver){
        return followMapper.toFollowDriver(user, driver);
    }
}
