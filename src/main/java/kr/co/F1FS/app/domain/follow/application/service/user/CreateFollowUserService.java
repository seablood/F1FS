package kr.co.F1FS.app.domain.follow.application.service.user;

import kr.co.F1FS.app.domain.follow.application.port.in.user.CreateFollowUserUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.user.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowUserService implements CreateFollowUserUseCase {
    private final FollowUserJpaPort followUserJpaPort;
    private final FollowUserDomainService followUserDomainService;

    @Override
    public void save(User followerUser, User followeeUser) {
        FollowUser followUser = followUserDomainService.createEntity(followerUser, followeeUser);

        followUserJpaPort.save(followUser);
    }
}
