package kr.co.F1FS.app.domain.follow.application.service;

import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.FollowUserUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowUserPort;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowUserService implements FollowUserUseCase {
    private final FollowMapper followMapper;
    private final FollowUserJpaPort followUserJpaPort;
    private final UserUseCase userUseCase;
    private final FollowUserPort followUserPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Transactional
    public void toggle(User followerUser, String followeeNickname){
        User followeeUser = followUserPort.findByNicknameNotDTO(followeeNickname);
        cacheEvictUtil.evictCachingUser(followerUser);
        cacheEvictUtil.evictCachingUser(followeeUser);

        if(isFollowed(followerUser, followeeUser)){
            FollowUser followUser = followUserJpaPort.findByFollowerUserAndFolloweeUser(followerUser, followeeUser);
            followUserJpaPort.delete(followUser);
            userUseCase.decreaseFollow(followerUser, followeeUser);
            return;
        }

        FollowUser followUser = followMapper.toFollowUser(followerUser, followeeUser);
        followUserJpaPort.save(followUser);
        userUseCase.increaseFollow(followerUser, followeeUser);

        followUserPort.saveAndFlush(followerUser);
        followUserPort.saveAndFlush(followeeUser);
    }

    public List<ResponseFollowUserDTO> findFollowers(String nickname){
        User user = followUserPort.findByNicknameNotDTO(nickname);
        return followUserJpaPort.findByFolloweeUser(user);
    }

    public List<ResponseFollowUserDTO> findFollowees(String nickname){
        User user = followUserPort.findByNicknameNotDTO(nickname);
        return followUserJpaPort.findByFollowerUser(user);
    }

    public List<ResponseFollowUserDTO> findFollowersAuth(User user){
        return followUserJpaPort.findByFolloweeUser(user);
    }

    public List<ResponseFollowUserDTO> findFolloweesAuth(User user){
        return followUserJpaPort.findByFollowerUser(user);
    }

    public List<ResponseUserIdDTO> findFollowersNotDTO(User user){ // DTO 매핑 고려 대상 메서드
        return followUserJpaPort.findByFolloweeUserId(user);
    }

    public boolean isFollowed(User followerUser, User followeeUser){
        return followUserJpaPort.existsFollowUserByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }
}
