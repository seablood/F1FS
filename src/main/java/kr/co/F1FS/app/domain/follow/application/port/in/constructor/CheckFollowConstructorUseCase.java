package kr.co.F1FS.app.domain.follow.application.port.in.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CheckFollowConstructorUseCase {
    boolean existsFollowConstructorByFollowerUserAndFolloweeConstructor(User user, Constructor constructor);
}
