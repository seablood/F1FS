package kr.co.F1FS.app.domain.follow.application.service.user;

import kr.co.F1FS.app.domain.follow.application.port.in.user.CheckFollowUserUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.user.FollowUserJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckFollowUserService implements CheckFollowUserUseCase {
    private final FollowUserJpaPort followUserJpaPort;

    @Override
    public boolean existsByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId) {
        return followUserJpaPort.existsByFollowerUserAndFolloweeUser(followerUserId, followeeUserId);
    }
}
