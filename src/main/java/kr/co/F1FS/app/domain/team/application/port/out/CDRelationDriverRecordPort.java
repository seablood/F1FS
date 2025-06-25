package kr.co.F1FS.app.domain.team.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;

public interface CDRelationDriverRecordPort {
    void save(Driver driver, CurrentSeason currentSeason);
}
