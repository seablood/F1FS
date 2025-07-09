package kr.co.F1FS.app.domain.follow.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;

public interface FollowDriverPort {
    Driver findByIdNotDTO(Long id);
    void saveAndFlush(Driver driver);
}
