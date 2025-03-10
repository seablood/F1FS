package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.FollowConstructor;
import kr.co.F1FS.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowConstructorRepository extends JpaRepository<FollowConstructor, Long> {
    FollowConstructor findByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
    List<FollowConstructor> findByFollowerUser(User followerUser);
    boolean existsFollowConstructorByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
}
