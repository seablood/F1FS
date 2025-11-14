package kr.co.F1FS.app.domain.constructor.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface ConstructorRecordRelationJpaPort {
    ConstructorRecordRelation save(ConstructorRecordRelation relation);
    List<ConstructorRecordRelation> findConstructorRecordRelationsByRacingClassAndEntryClassSeason(RacingClass racingClass, boolean entryClassSeason);
    ConstructorRecordRelation findByConstructorInfo(Constructor constructor);
}
