package kr.co.F1FS.app.domain.follow.application.service.user;

import kr.co.F1FS.app.domain.follow.application.port.in.user.DeleteFollowUserUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.user.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFollowUserService implements DeleteFollowUserUseCase {
    private final FollowUserJpaPort followUserJpaPort;

    @Override
    public void delete(FollowUser followUser) {
        followUserJpaPort.delete(followUser);
    }
}
