package kr.co.F1FS.app.domain.follow.infrastructure.adapter.user;

import kr.co.F1FS.app.domain.follow.application.port.out.user.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowUserRepository;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.dsl.FollowUserDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowUserJpaAdapter implements FollowUserJpaPort {
    private final FollowUserRepository followUserRepository;
    private final FollowUserDSLRepository followUserDSLRepository;

    @Override
    public FollowUser save(FollowUser followUser) {
        return followUserRepository.save(followUser);
    }

    @Override
    public FollowUser findByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId) {
        return followUserDSLRepository.findByFollowerUserAndFolloweeUser(followerUserId, followeeUserId);
    }

    @Override
    public List<FollowUser> findAllByFollowerUser(Long followerUserId) {
        return followUserDSLRepository.findAllByFollowerUser(followerUserId);
    }

    @Override
    public List<FollowUser> findAllByFolloweeUser(Long followeeUserId) {
        return followUserDSLRepository.findAllByFolloweeUser(followeeUserId);
    }

    @Override
    public boolean existsByFollowerUserAndFolloweeUser(Long followerUserId, Long followeeUserId) {
        return followUserDSLRepository.existsByFollowerUserAndFolloweeUser(followerUserId, followeeUserId);
    }

    @Override
    public void delete(FollowUser followUser) {
        followUserRepository.delete(followUser);
    }
}
