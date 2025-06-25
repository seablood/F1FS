package kr.co.F1FS.app.domain.team.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;

public interface CDRelationDriverPort {
    Driver findByNumber(Integer number);
    void saveAndFlush(Driver driver);
    Driver findById(Long id);
}
