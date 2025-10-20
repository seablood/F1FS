package kr.co.F1FS.app.domain.constructor.application.port.out;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface ConstructorRecordPort {
    void save(CurrentSeason currentSeason, SinceDebut sinceDebut);
    void saveAndFlush(CurrentSeason currentSeason);
}
