package kr.co.F1FS.app.domain.driver.application.port.in.debut;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;

public interface QueryDriverDebutRelationUseCase {
    DriverDebutRelation findDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver);
}
