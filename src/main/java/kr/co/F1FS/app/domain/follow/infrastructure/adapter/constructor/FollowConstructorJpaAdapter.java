package kr.co.F1FS.app.domain.follow.infrastructure.adapter.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowConstructorRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowConstructorJpaAdapter implements FollowConstructorJpaPort {
    private final FollowConstructorRepository followConstructorRepository;
    private final FollowMapper followMapper;

    @Override
    public FollowConstructor save(FollowConstructor followConstructor) {
        return followConstructorRepository.save(followConstructor);
    }

    @Override
    public FollowConstructor findByFollowerUserAndFolloweeConstructor(User user, Constructor constructor) {
        return followConstructorRepository.findByFollowerUserAndFolloweeConstructor(user, constructor);
    }

    @Override
    public List<FollowConstructor> findByFollowerUser(User user) {
        return followConstructorRepository.findByFollowerUser(user);
    }

    @Override
    public boolean existsFollowConstructorByFollowerUserAndFolloweeConstructor(User user, Constructor constructor) {
        return followConstructorRepository.existsFollowConstructorByFollowerUserAndFolloweeConstructor(
                user, constructor
        );
    }

    @Override
    public void delete(FollowConstructor followConstructor) {
        followConstructorRepository.delete(followConstructor);
    }
}
