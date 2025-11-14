package kr.co.F1FS.app.domain.team.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;

public interface ConstructorDriverRelationDriverUseCase {
    ConstructorDriverRelation save(Constructor constructor, Driver driver);
}
