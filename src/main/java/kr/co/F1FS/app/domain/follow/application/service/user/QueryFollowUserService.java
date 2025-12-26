package kr.co.F1FS.app.domain.follow.application.service.user;

import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.user.QueryFollowUserUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.user.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.presentation.dto.user.ResponseFollowUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFollowUserService implements QueryFollowUserUseCase {
    private final FollowUserJpaPort followUserJpaPort;
    private final FollowMapper followMapper;

    @Override
    public FollowUser findByFollowerUserAndFolloweeUser(User followerUser, User followeeUser) {
        return followUserJpaPort.findByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }

    @Override
    @Cacheable(value = "FollowingUser", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<ResponseFollowUserDTO> findByFollowerUserForDTO(User user) {
        return followUserJpaPort.findByFollowerUser(user).stream()
                .map(followUser -> followUser.getFolloweeUser())
                .map(user1 -> followMapper.toResponseFollowUserDTO(user1))
                .toList();
    }

    @Override
    @Cacheable(value = "FollowerUser", key = "#user.id", cacheManager = "redisLongCacheManager")
    public List<ResponseFollowUserDTO> findByFolloweeUserForDTO(User user) {
        return followUserJpaPort.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(user1 -> followMapper.toResponseFollowUserDTO(user1))
                .toList();
    }

    @Override
    public List<ResponseUserIdDTO> findByFolloweeUserIdForDTO(User user) {
        return followUserJpaPort.findByFolloweeUser(user).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(user1 -> followMapper.toResponseUserIdDTO(user1))
                .toList();
    }
}
