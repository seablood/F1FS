package kr.co.F1FS.app.domain.follow.application.port.in.driver;

import kr.co.F1FS.app.domain.follow.domain.FollowDriver;

public interface DeleteFollowDriverUseCase {
    void delete(FollowDriver followDriver);
}
