package kr.co.F1FS.app.domain.driver.application.port.in;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.presentation.dto.ResponseDriverStandingDTO;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;

import java.util.List;

public interface DriverRecordRelationUseCase {
    void save(Driver driver, CurrentSeason currentSeason);
    List<ResponseDriverStandingDTO> getDriverStandingList(String racingClassCode);
    void updateChampionshipRank(String racingClassCode);
    DriverRecordRelation getRecordByDriverAndRacingClass(Driver driver);
}
