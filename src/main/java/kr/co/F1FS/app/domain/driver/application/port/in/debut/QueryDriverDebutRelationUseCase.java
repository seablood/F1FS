package kr.co.F1FS.app.domain.driver.application.port.in.debut;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.global.util.RacingClass;

public interface QueryDriverDebutRelationUseCase {
    DriverDebutRelation findByDriverAndRacingClass(Driver driver);
}
