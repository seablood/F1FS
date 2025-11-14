package kr.co.F1FS.app.domain.driver.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.global.util.RacingClass;

public interface DriverDebutRelationJpaPort {
    DriverDebutRelation save(DriverDebutRelation debutRelation);
    DriverDebutRelation findDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass);
    boolean existsDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass);
}
