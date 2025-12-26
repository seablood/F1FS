package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.constructor.QueryFollowConstructorUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.constructor.FollowConstructorJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFollowConstructorService implements QueryFollowConstructorUseCase {
    private final FollowConstructorJpaPort followConstructorJpaPort;
    private final FollowMapper followMapper;

    @Override
    public FollowConstructor findByFollowerUserAndFolloweeConstructor(User user, Constructor constructor) {
        return followConstructorJpaPort.findByFollowerUserAndFolloweeConstructor(user, constructor);
    }

    @Override
    @Cacheable(value = "FollowingConstructor", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<ResponseFollowConstructorDTO> findByFollowerUserForDTO(User user) {
        return followConstructorJpaPort.findByFollowerUser(user).stream()
                .map(followConstructor -> followConstructor.getFolloweeConstructor())
                .map(followeeConstructor -> followMapper.toResponseFollowConstructorDTO(followeeConstructor))
                .toList();
    }
}
