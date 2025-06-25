package kr.co.F1FS.app.domain.team.application.port.out;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface CDRelationRecordPort {
    void save(CurrentSeason currentSeason, SinceDebut sinceDebut);
}
