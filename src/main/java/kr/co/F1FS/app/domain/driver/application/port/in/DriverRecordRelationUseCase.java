package kr.co.F1FS.app.domain.driver.application.port.in;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;

public interface DriverRecordRelationUseCase {
    void save(Driver driver, CurrentSeason currentSeason);
}
