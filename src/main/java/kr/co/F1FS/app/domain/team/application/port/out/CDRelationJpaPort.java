package kr.co.F1FS.app.domain.team.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface CDRelationJpaPort {
    ConstructorDriverRelation save(ConstructorDriverRelation relation);
    ConstructorDriverRelation saveAndFlush(ConstructorDriverRelation relation);
    boolean existsConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass);
    boolean existsConstructorDriverRelationByDriverAndConstructor(Driver driver, Constructor constructor);
    ConstructorDriverRelation findConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass);
    List<ConstructorDriverRelation> findConstructorDriverRelationByDriver(Driver driver);
    List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor);
    void delete(ConstructorDriverRelation relation);
}
