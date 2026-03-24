package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import kr.co.F1FS.app.domain.follow.domain.FollowDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowDriverRepository extends JpaRepository<FollowDriver, Long> {
}
