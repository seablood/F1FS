package kr.co.F1FS.app.domain.team.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface ConstructorDriverRelationUseCase {
    ConstructorDriverRelation save(Constructor constructor, Driver driver);
    List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor);
    void transfer(Integer number, String constructorName);
    void modifyRacingClass(Driver driver, RacingClass racingClass);
    void delete(Long id, String option);
    void option(Driver driver, String option);
}
