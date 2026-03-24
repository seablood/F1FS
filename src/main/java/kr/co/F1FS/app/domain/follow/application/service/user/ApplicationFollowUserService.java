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

        if(checkFollowUserUseCase.existsByFollowerUserAndFolloweeUser(followerUser.getId(), followeeUser.getId())){
            FollowUser followUser = queryFollowUserUseCase.findByFollowerUserAndFolloweeUser(followerUser.getId(), followeeUser.getId());
            deleteFollowUserUseCase.delete(followUser);
            updateUserUseCase.decreaseFollow(followerUser, followeeUser);
            return;
        }

        createFollowUserUseCase.save(followerUser, followeeUser);
        updateUserUseCase.increaseFollow(followerUser, followeeUser);
    }

    @Override
    @Transactional
    public List<ResponseFollowUserDTO> getFollowersByNickname(String nickname){
        User user = queryUserUseCase.findByNickname(nickname);
        return queryFollowUserUseCase.findAllByFolloweeUserForDTO(user.getId());
    }

    @Override
    @Transactional
    public List<ResponseFollowUserDTO> getFolloweesByNickname(String nickname){
        User user = queryUserUseCase.findByNickname(nickname);
        return queryFollowUserUseCase.findAllByFollowerUserForDTO(user.getId());
    }

    @Override
    @Transactional
    public List<ResponseFollowUserDTO> getFollowersByUser(User user){
        return queryFollowUserUseCase.findAllByFolloweeUserForDTO(user.getId());
    }

    @Override
    @Transactional
    public List<ResponseFollowUserDTO> getFolloweesByUser(User user){
        return queryFollowUserUseCase.findAllByFollowerUserForDTO(user.getId());
    }
}
