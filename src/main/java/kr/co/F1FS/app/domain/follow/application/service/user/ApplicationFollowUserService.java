package kr.co.F1FS.app.domain.follow.application.service.user;

import kr.co.F1FS.app.domain.follow.application.port.in.user.*;
import kr.co.F1FS.app.domain.follow.presentation.dto.user.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationFollowUserService implements FollowUserUseCase {
    private final CreateFollowUserUseCase createFollowUserUseCase;
    private final DeleteFollowUserUseCase deleteFollowUserUseCase;
    private final QueryFollowUserUseCase queryFollowUserUseCase;
    private final CheckFollowUserUseCase checkFollowUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public void toggle(User followerUser, String followeeNickname){
        User followeeUser = queryUserUseCase.findByNickname(followeeNickname);
        cacheEvictUtil.evictCachingUser(followerUser);
        cacheEvictUtil.evictCachingUser(followeeUser);

        if(checkFollowUserUseCase.existsFollowUserByFollowerUserAndFolloweeUser(followerUser, followeeUser)){
            FollowUser followUser = queryFollowUserUseCase.findByFollowerUserAndFolloweeUser(followerUser, followeeUser);
            deleteFollowUserUseCase.delete(followUser);
            updateUserUseCase.decreaseFollow(followerUser, followeeUser);
            return;
        }

        createFollowUserUseCase.save(followerUser, followeeUser);
        updateUserUseCase.increaseFollow(followerUser, followeeUser);
    }

    @Override
    public List<ResponseFollowUserDTO> findFollowers(String nickname){
        User user = queryUserUseCase.findByNickname(nickname);
        return queryFollowUserUseCase.findByFolloweeUserForDTO(user);
    }

    @Override
    public List<ResponseFollowUserDTO> findFollowees(String nickname){
        User user = queryUserUseCase.findByNickname(nickname);
        return queryFollowUserUseCase.findByFollowerUserForDTO(user);
    }

    @Override
    public List<ResponseFollowUserDTO> findFollowersAuth(User user){
        return queryFollowUserUseCase.findByFolloweeUserForDTO(user);
    }

    @Override
    public List<ResponseFollowUserDTO> findFolloweesAuth(User user){
        return queryFollowUserUseCase.findByFollowerUserForDTO(user);
    }
}
