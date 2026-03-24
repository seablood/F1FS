package kr.co.F1FS.app.domain.driver.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;
import java.util.Optional;

public interface DriverRecordRelationDSLRepository {
    Optional<DriverRecordRelation> findByDriverAndRacingClass(Long driverId, RacingClass racingClass);
    List<ResponseDriverStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass);
    List<DriverRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass);
}
