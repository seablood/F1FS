package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.port.in.constructor.CreateFollowConstructorUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowConstructorService implements CreateFollowConstructorUseCase {
    private final FollowConstructorJpaPort followConstructorJpaPort;
    private final FollowConstructorDomainService followConstructorDomainService;

    @Override
    public FollowConstructor save(User user, Constructor constructor) {
        FollowConstructor followConstructor = followConstructorDomainService.createEntity(user, constructor);

        return followConstructorJpaPort.save(followConstructor);
    }
}
