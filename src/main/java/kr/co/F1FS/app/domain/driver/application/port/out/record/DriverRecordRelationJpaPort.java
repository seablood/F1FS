package kr.co.F1FS.app.domain.driver.application.port.out.record;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface DriverRecordRelationJpaPort {
    DriverRecordRelation save(DriverRecordRelation relation);
    List<DriverRecordRelation> findDriverRecordRelationsByRacingClassAndEntryClassSeason(RacingClass racingClass, boolean entryClassSeason);
    DriverRecordRelation findDriverRecordRelationByDriverInfoAndRacingClass(Driver driver, RacingClass racingClass);
}
