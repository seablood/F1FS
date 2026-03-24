package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.follow.application.port.in.constructor.CheckFollowConstructorUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckFollowConstructorService implements CheckFollowConstructorUseCase {
    private final FollowConstructorJpaPort followConstructorJpaPort;

    @Override
    public boolean existsByUserAndConstructor(Long userId, Long constructorId) {
        return followConstructorJpaPort.existsByUserAndConstructor(userId, constructorId);
    }
}
