package kr.co.F1FS.app.domain.driver.application.port.out.record;

import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface DriverRecordRelationJpaPort {
    DriverRecordRelation save(DriverRecordRelation relation);
    List<ResponseDriverStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass);
    List<DriverRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass);
    DriverRecordRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass);
}
