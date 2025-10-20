package kr.co.F1FS.app.domain.driver.application.port.out;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface DriverRecordPort {
    void save(CurrentSeason currentSeason, SinceDebut sinceDebut);
    void saveAndFlush(CurrentSeason currentSeason);
}
