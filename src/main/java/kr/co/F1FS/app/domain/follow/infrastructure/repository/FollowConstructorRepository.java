package kr.co.F1FS.app.domain.follow.infrastructure.repository;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowConstructorRepository extends JpaRepository<FollowConstructor, Long> {
    FollowConstructor findByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
    List<FollowConstructor> findByFollowerUser(User followerUser);
    boolean existsFollowConstructorByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
}
