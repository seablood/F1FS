package kr.co.F1FS.app.domain.follow.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowUserPort;
import kr.co.F1FS.app.domain.follow.presentation.dto.ResponseFollowUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowUserService {
    private final FollowMapper followMapper;
    private final FollowUserRepository followUserRepository;
    private final FollowUserPort followUserPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Transactional
    public void toggle(User followerUser, String followeeNickname){
        User followeeUser = followUserPort.findByNicknameNotDTO(followeeNickname);
        cacheEvictUtil.evictCachingUser(followerUser);
        cacheEvictUtil.evictCachingUser(followeeUser);

        if(isFollowed(followerUser, followeeUser)){
            FollowUser followUser = followUserRepository.findByFollowerUserAndFolloweeUser(followerUser, followeeUser);
            followUserRepository.delete(followUser);
            followUserPort.decreaseFollow(followerUser, followeeUser);
            return;
        }

        FollowUser followUser = followMapper.toFollowUser(followerUser, followeeUser);
        followUserRepository.save(followUser);
        followUserPort.increaseFollow(followerUser, followeeUser);
    }

    public List<ResponseFollowUserDTO> findFollowers(String nickname){
        User user = followUserPort.findByNicknameNotDTO(nickname);
        return followUserRepository.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(followerUser -> followMapper.toResponseFollowUserDTO(followerUser))
                .toList();
    }

    public List<ResponseFollowUserDTO> findFollowees(String nickname){
        User user = followUserPort.findByNicknameNotDTO(nickname);
        return followUserRepository.findByFollowerUser(user).stream()
                .map(followUser -> followUser.getFolloweeUser())
                .map(followeeUser -> followMapper.toResponseFollowUserDTO(followeeUser))
                .toList();
    }

    public List<ResponseFollowUserDTO> findFollowersAuth(User user){
        return followUserRepository.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(followerUser -> followMapper.toResponseFollowUserDTO(followerUser))
                .toList();
    }

    public List<ResponseFollowUserDTO> findFolloweesAuth(User user){
        return followUserRepository.findByFollowerUser(user).stream()
                .map(followUser -> followUser.getFolloweeUser())
                .map(followeeUser -> followMapper.toResponseFollowUserDTO(followeeUser))
                .toList();
    }

    public List<ResponseUserIdDTO> findFollowersNotDTO(User user){ // DTO 매핑 고려 대상 메서드
        return followUserRepository.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(followerUser -> followMapper.toResponseUserIdDTO(followerUser))
                .toList();
    }

    public boolean isFollowed(User followerUser, User followeeUser){
        return followUserRepository.existsFollowUserByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }
}
