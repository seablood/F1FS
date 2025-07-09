package kr.co.F1FS.app.domain.constructor.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface ConstructorRecordRelationUseCase {
    void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut);
    ConstructorRecordRelation findByConstructor(Constructor constructor);
}
