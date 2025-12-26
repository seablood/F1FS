package kr.co.F1FS.app.domain.constructor.application.port.in.record;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface CreateConstructorRecordRelationUseCase {
    void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut);
}
