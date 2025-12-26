package kr.co.F1FS.app.domain.driver.application.port.in.record;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;

public interface CreateDriverRecordRelationUseCase {
    void save(Driver driver, CurrentSeason currentSeason);
}
