package kr.co.F1FS.app.domain.follow.application.service;

import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.FollowConstructorUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowConstructorPort;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowConstructorRepository;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowConstructorService implements FollowConstructorUseCase {
    private final FollowMapper followMapper;
    private final FollowConstructorRepository followConstructorRepository;
    private final CacheEvictUtil cacheEvictUtil;
    private final ConstructorUseCase constructorUseCase;
    private final FollowConstructorPort followConstructorPort;

    @Transactional
    @CacheEvict(value = "FollowingConstructor", key = "#user.id", cacheManager = "redisLongCacheManager")
    public void toggle(User user, Long id){
        Constructor constructor = followConstructorPort.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(isFollowed(user, constructor)){
            FollowConstructor followConstructor = followConstructorRepository.findByFollowerUserAndFolloweeConstructor(
                    user, constructor
            );
            followConstructorRepository.delete(followConstructor);
            constructorUseCase.decreaseFollower(constructor);
            return;
        }

        FollowConstructor followConstructor = followMapper.toFollowConstructor(user, constructor);
        followConstructorRepository.save(followConstructor);
        constructorUseCase.increaseFollower(constructor);

        followConstructorPort.saveAndFlush(constructor);
    }

    @Cacheable(value = "FollowingConstructor", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<ResponseFollowConstructorDTO> getFollowingConstructor(User user){
        return followConstructorRepository.findByFollowerUser(user).stream()
                .map(followConstructor -> followConstructor.getFolloweeConstructor())
                .map(followeeConstructor -> followMapper.toResponseFollowConstructorDTO(followeeConstructor))
                .toList();
    }

    public boolean isFollowed(User user, Constructor constructor){
        return followConstructorRepository.existsFollowConstructorByFollowerUserAndFolloweeConstructor(
                user, constructor);
    }
}
