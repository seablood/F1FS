package kr.co.F1FS.app.domain.constructor.application.port.out.record;

import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface ConstructorRecordRelationJpaPort {
    ConstructorRecordRelation save(ConstructorRecordRelation relation);
    List<ResponseConstructorStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass);
    List<ConstructorRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass);
    ConstructorRecordRelation findByConstructor(Long constructorId);
}
