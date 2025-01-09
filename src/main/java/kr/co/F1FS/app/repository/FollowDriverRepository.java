package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.FollowDriver;
import kr.co.F1FS.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowDriverRepository extends JpaRepository<FollowDriver, Long> {
    FollowDriver findByFollowerUserAndFolloweeDriver(User user, Driver driver);
    boolean existsFollowDriverByFollowerUserAndFolloweeDriver(User user, Driver driver);
}
