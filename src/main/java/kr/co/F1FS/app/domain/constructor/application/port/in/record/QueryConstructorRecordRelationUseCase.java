package kr.co.F1FS.app.domain.constructor.application.port.in.record;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface QueryConstructorRecordRelationUseCase {
    ConstructorRecordRelation findByConstructor(Constructor constructor);
    List<ResponseConstructorStandingDTO> findConstructorRecordRelationsByRacingClassAndEntryClassSeasonForDTO(RacingClass racingClass);
}
