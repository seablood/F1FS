package kr.co.F1FS.app.domain.driver.application.port.in.record;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;

public interface QueryDriverRecordRelationUseCase {
    DriverRecordRelation findDriverRecordRelationByDriverInfoAndRacingClass(Driver driver);
}
