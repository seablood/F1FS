package kr.co.F1FS.app.domain.follow.infrastructure.adapter.user;

import kr.co.F1FS.app.domain.follow.application.port.out.user.FollowUserJpaPort;
import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.follow.infrastructure.repository.FollowUserRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowUserJpaAdapter implements FollowUserJpaPort {
    private final FollowUserRepository followUserRepository;

    @Override
    public FollowUser save(FollowUser followUser) {
        return followUserRepository.save(followUser);
    }

    @Override
    public FollowUser findByFollowerUserAndFolloweeUser(User followerUser, User followeeUser) {
        return followUserRepository.findByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }

    @Override
    public List<FollowUser> findByFolloweeUser(User user) {
        return followUserRepository.findByFolloweeUser(user);
    }

    @Override
    public List<FollowUser> findByFollowerUser(User user) {
        return followUserRepository.findByFollowerUser(user);
    }

    @Override
    public boolean existsFollowUserByFollowerUserAndFolloweeUser(User followerUser, User followeeUser) {
        return followUserRepository.existsFollowUserByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }

    @Override
    public void delete(FollowUser followUser) {
        followUserRepository.delete(followUser);
    }
}
