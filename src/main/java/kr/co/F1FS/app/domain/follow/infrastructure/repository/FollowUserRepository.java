package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {
    FollowUser findByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    List<FollowUser> findByFollowerUser(User followerUser);
    List<FollowUser> findByFolloweeUser(User followeeUser);
    boolean existsFollowUserByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
}
