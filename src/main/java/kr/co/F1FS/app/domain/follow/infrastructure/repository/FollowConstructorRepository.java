package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowConstructorRepository extends JpaRepository<FollowConstructor, Long> {
}
