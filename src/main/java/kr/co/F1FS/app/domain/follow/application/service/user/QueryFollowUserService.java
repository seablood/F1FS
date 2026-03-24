package kr.co.F1FS.app.domain.follow.application.service.user;

import kr.co.F1FS.app.domain.follow.application.mapper.FollowMapper;
import kr.co.F1FS.app.domain.follow.application.port.in.user.QueryFollowUserUseCase;
import kr.co.F1FS.app.domain.follow.application.port.out.user.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.presentation.dto.user.ResponseFollowUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFollowUserService implements QueryFollowUserUseCase {
    private final FollowUserJpaPort followUserJpaPort;
    private final FollowMapper followMapper;

    @Override
    public FollowUser findByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId) {
        return followUserJpaPort.findByFollowerUserAndFolloweeUser(followerUserId, followeeUserId);
    }

    @Override
    public List<ResponseFollowUserDTO> findAllByFollowerUserForDTO(Long followerUserId) {
        return followUserJpaPort.findAllByFollowerUser(followerUserId).stream()
                .map(followUser -> followUser.getFolloweeUser())
                .map(user1 -> followMapper.toResponseFollowUserDTO(user1))
                .toList();
    }

    @Override
    public List<ResponseFollowUserDTO> findAllByFolloweeUserForDTO(Long followeeUserId) {
        return followUserJpaPort.findAllByFolloweeUser(followeeUserId).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(user1 -> followMapper.toResponseFollowUserDTO(user1))
                .toList();
    }

    @Override
    public List<ResponseUserIdDTO> findByFolloweeUserIdForDTO(Long userId) {
        return followUserJpaPort.findAllByFolloweeUser(userId).stream()
                .map(followUser -> followUser.getFollowerUser())
                .map(user1 -> followMapper.toResponseUserIdDTO(user1))
                .toList();
    }
}
