package kr.co.F1FS.app.domain.follow.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;

public interface FollowConstructorPort {
    Constructor findByIdNotDTO(Long id);
    void increaseFollower(Constructor constructor);
    void decreaseFollower(Constructor constructor);
}
