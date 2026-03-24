package kr.co.F1FS.app.domain.constructor.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;
import java.util.Optional;

public interface ConstructorRecordRelationDSLRepository {
    Optional<ConstructorRecordRelation> findByConstructor(Long constructorId);
    List<ResponseConstructorStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass);
    List<ConstructorRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass);
}
