package kr.co.F1FS.app.domain.follow.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.UpdateConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.application.port.in.constructor.*;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.follow.presentation.dto.constructor.ResponseFollowConstructorDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationFollowConstructorService implements FollowConstructorUseCase {
    private final CreateFollowConstructorUseCase createFollowConstructorUseCase;
    private final DeleteFollowConstructorUseCase deleteFollowConstructorUseCase;
    private final QueryFollowConstructorUseCase queryFollowConstructorUseCase;
    private final CheckFollowConstructorUseCase checkFollowConstructorUseCase;
    private final UpdateConstructorUseCase updateConstructorUseCase;
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    @CacheEvict(value = "FollowingConstructor", key = "#user.id", cacheManager = "redisLongCacheManager")
    public void toggle(User user, Long id){
        Constructor constructor = queryConstructorUseCase.findById(id);
        cacheEvictUtil.evictCachingConstructor(constructor);

        if(checkFollowConstructorUseCase.existsFollowConstructorByFollowerUserAndFolloweeConstructor(user, constructor)){
            FollowConstructor followConstructor = queryFollowConstructorUseCase.findByFollowerUserAndFolloweeConstructor(
                    user, constructor
            );
            deleteFollowConstructorUseCase.delete(followConstructor);
            updateConstructorUseCase.decreaseFollower(constructor);
            return;
        }

        createFollowConstructorUseCase.save(user, constructor);
        updateConstructorUseCase.increaseFollower(constructor);
    }

    @Override
    public List<ResponseFollowConstructorDTO> getFollowingConstructor(User user){
        return queryFollowConstructorUseCase.findByFollowerUserForDTO(user);
    }

    @Override
    public List<ResponseFollowConstructorDTO> getFollowingConstructorByNickname(String nickname) {
        User user = queryUserUseCase.findByNickname(nickname);
        return queryFollowConstructorUseCase.findByFollowerUserForDTO(user);
    }
}
