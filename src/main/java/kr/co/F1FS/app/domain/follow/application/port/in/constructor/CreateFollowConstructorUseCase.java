package kr.co.F1FS.app.domain.follow.application.port.in.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.follow.domain.FollowConstructor;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateFollowConstructorUseCase {
    FollowConstructor save(User user, Constructor constructor);
}
