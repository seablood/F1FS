package kr.co.F1FS.app.domain.driver.application.port.in;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;

public interface DriverDebutRelationUseCase {
    void save(Driver driver, SinceDebut sinceDebut);
    boolean existsDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass);
    DriverDebutRelation getSinceDebutByDriverAndRacingClass(Driver driver);
}
