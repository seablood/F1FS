package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.follow.application.port.in.constructor.DeleteFollowConstructorUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFollowConstructorService implements DeleteFollowConstructorUseCase {
    private final FollowConstructorJpaPort followConstructorJpaPort;

    @Override
    public void delete(FollowConstructor followConstructor) {
        followConstructorJpaPort.delete(followConstructor);
    }
}
