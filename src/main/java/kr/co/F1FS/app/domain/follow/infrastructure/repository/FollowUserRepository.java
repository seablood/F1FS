package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import kr.co.F1FS.app.domain.follow.domain.FollowUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {
}
