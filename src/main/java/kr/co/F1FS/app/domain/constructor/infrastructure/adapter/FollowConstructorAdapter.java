package kr.co.F1FS.app.domain.constructor.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.application.service.ConstructorService;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowConstructorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowConstructorAdapter implements FollowConstructorPort {
    private final ConstructorService constructorService;

    @Override
    public Constructor findByIdNotDTO(Long id) {
        return constructorService.findByIdNotDTO(id);
    }

    @Override
    public void increaseFollower(Constructor constructor) {
        constructor.increaseFollower();
    }

    @Override
    public void decreaseFollower(Constructor constructor) {
        constructor.decreaseFollower();
    }
}
