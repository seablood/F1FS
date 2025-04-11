package kr.co.F1FS.app.domain.repository.rdb.follow;

import kr.co.F1FS.app.domain.model.rdb.FollowUser;
import kr.co.F1FS.app.domain.model.rdb.User;
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
