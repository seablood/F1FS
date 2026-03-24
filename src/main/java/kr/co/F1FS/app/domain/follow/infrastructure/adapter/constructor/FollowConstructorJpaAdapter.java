package kr.co.F1FS.app.domain.follow.infrastructure.adapter.constructor;

import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowConstructorRepository;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl.FollowConstructorDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowConstructorJpaAdapter implements FollowConstructorJpaPort {
    private final FollowConstructorRepository followConstructorRepository;
    private final FollowConstructorDSLRepository followConstructorDSLRepository;

    @Override
    public FollowConstructor save(FollowConstructor followConstructor) {
        return followConstructorRepository.save(followConstructor);
    }

    @Override
    public FollowConstructor findByUserAndConstructor(Long userId, Long constructorId) {
        return followConstructorDSLRepository.findByUserAndConstructor(userId, constructorId);
    }

    @Override
    public List<FollowConstructor> findAllByUser(Long userId) {
        return followConstructorDSLRepository.findAllByUser(userId);
    }

    @Override
    public boolean existsByUserAndConstructor(Long userId, Long constructorId) {
        return followConstructorDSLRepository.existsByUserAndConstructor(userId, constructorId);
    }

    @Override
    public void delete(FollowConstructor followConstructor) {
        followConstructorRepository.delete(followConstructor);
    }
}
