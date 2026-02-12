package kr.co.F1FS.app.domain.driver.application.port.in.record;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface QueryDriverRecordRelationUseCase {
    DriverRecordRelation findDriverRecordRelationByDriverInfoAndRacingClass(Driver driver);
    List<ResponseDriverStandingDTO> findDriverRecordRelationsByRacingClassAndEntryClassSeasonForDTO(RacingClass racingClass);
}
