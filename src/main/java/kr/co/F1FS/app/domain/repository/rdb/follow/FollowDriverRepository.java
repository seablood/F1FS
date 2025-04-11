package kr.co.F1FS.app.domain.repository.rdb.follow;

import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.domain.model.rdb.FollowDriver;
import kr.co.F1FS.app.domain.model.rdb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowDriverRepository extends JpaRepository<FollowDriver, Long> {
    FollowDriver findByFollowerUserAndFolloweeDriver(User user, Driver driver);
    List<FollowDriver> findByFollowerUser(User followerUser);
    boolean existsFollowDriverByFollowerUserAndFolloweeDriver(User user, Driver driver);
}
