package kr.co.F1FS.app.domain.constructor.infrastructure.adapter.record;

import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRecordRelationRepository;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.dsl.ConstructorRecordRelationDSLRepository;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorException;
import kr.co.F1FS.app.global.util.exception.constructor.ConstructorExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConstructorRecordRelationJpaAdapter implements ConstructorRecordRelationJpaPort {
    private final ConstructorRecordRelationRepository relationRepository;
    private final ConstructorRecordRelationDSLRepository relationDSLRepository;

    @Override
    public ConstructorRecordRelation save(ConstructorRecordRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    public List<ResponseConstructorStandingDTO> findAllByRacingClassAndEntryClassSeason(RacingClass racingClass) {
        return relationDSLRepository.findAllByRacingClassAndEntryClassSeason(racingClass);
    }

    @Override
    public List<ConstructorRecordRelation> findAllByRacingClassAndEntryClassSeasonNotDTO(RacingClass racingClass) {
        return relationDSLRepository.findAllByRacingClassAndEntryClassSeasonNotDTO(racingClass);
    }

    @Override
    public ConstructorRecordRelation findByConstructor(Long constructorId) {
        return relationDSLRepository.findByConstructor(constructorId)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_RECORD_ERROR));
    }
}
