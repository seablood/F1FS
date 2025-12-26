package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.port.in.constructor.CheckFollowConstructorUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckFollowConstructorService implements CheckFollowConstructorUseCase {
    private final FollowConstructorJpaPort followConstructorJpaPort;

    @Override
    public boolean existsFollowConstructorByFollowerUserAndFolloweeConstructor(User user, Constructor constructor) {
        return followConstructorJpaPort.existsFollowConstructorByFollowerUserAndFolloweeConstructor(user, constructor);
    }
}
