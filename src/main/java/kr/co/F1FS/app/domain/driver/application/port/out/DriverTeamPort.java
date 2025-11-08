package kr.co.F1FS.app.domain.driver.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;

public interface DriverTeamPort {
    void save(ConstructorDriverRelation relation);
    ConstructorDriverRelation toConstructorDriverRelation(Constructor constructor, Driver driver);
}
