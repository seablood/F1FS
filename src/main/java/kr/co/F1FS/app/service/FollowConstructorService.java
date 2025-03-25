package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.FollowConstructor;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.FollowConstructorRepository;
import kr.co.F1FS.app.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowConstructorService {
    private final FollowConstructorRepository followConstructorRepository;
    private final CacheEvictUtil cacheEvictUtil;
    private final ConstructorService constructorService;

    @Transactional
    @CacheEvict(value = "FollowingConstructor", key = "#user.id", cacheManager = "redisLongCacheManager")
    public void toggle(User user, Long id){
        Constructor constructor = constructorService.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(isFollowed(user, constructor)){
            FollowConstructor followConstructor = followConstructorRepository.findByFollowerUserAndFolloweeConstructor(
                    user, constructor
            );
            followConstructorRepository.delete(followConstructor);
            constructor.decreaseFollower();
            return;
        }

        FollowConstructor followConstructor = FollowConstructor.builder()
                .followerUser(user)
                .followeeConstructor(constructor)
                .build();
        followConstructorRepository.save(followConstructor);
        constructor.increaseFollower();
    }

    @Cacheable(value = "FollowingConstructor", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<SimpleResponseConstructorDTO> getFollowingConstructor(User user){
        return followConstructorRepository.findByFollowerUser(user).stream()
                .map(followConstructor -> followConstructor.getFolloweeConstructor())
                .map(followeeConstructor -> SimpleResponseConstructorDTO.toDto(followeeConstructor))
                .toList();
    }

    public boolean isFollowed(User user, Constructor constructor){
        return followConstructorRepository.existsFollowConstructorByFollowerUserAndFolloweeConstructor(
                user, constructor);
    }
}
