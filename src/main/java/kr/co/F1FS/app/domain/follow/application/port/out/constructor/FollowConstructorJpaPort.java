package kr.co.F1FS.app.domain.follow.application.port.out.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FollowConstructorJpaPort {
    FollowConstructor save(FollowConstructor followConstructor);
    FollowConstructor findByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
    List<FollowConstructor> findByFollowerUser(User user);
    boolean existsFollowConstructorByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
    void delete(FollowConstructor followConstructor);
}
