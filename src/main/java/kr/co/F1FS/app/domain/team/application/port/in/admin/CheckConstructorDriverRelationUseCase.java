package kr.co.F1FS.app.domain.team.application.port.in.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;

public interface CheckConstructorDriverRelationUseCase {
    boolean existsConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass);
    boolean existsConstructorDriverRelationByDriverAndConstructor(Driver driver, Constructor constructor);
}
