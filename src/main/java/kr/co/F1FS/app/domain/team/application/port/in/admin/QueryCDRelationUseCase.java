package kr.co.F1FS.app.domain.team.application.port.in.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface QueryCDRelationUseCase {
    ConstructorDriverRelation findConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass);
    List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor);
}
