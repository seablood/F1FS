package kr.co.F1FS.app.domain.constructor.infrastructure.adapter.record;

import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.infrastructure.repository.ConstructorRecordRelationRepository;
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

    @Override
    public ConstructorRecordRelation save(ConstructorRecordRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    public List<ConstructorRecordRelation> findConstructorRecordRelationsByRacingClassAndEntryClassSeason(RacingClass racingClass, boolean entryClassSeason) {
        return relationRepository.findConstructorRecordRelationsByRacingClassAndEntryClassSeason(racingClass, entryClassSeason);
    }

    @Override
    public ConstructorRecordRelation findByConstructorInfo(Constructor constructor) {
        return relationRepository.findByConstructorInfo(constructor)
                .orElseThrow(() -> new ConstructorException(ConstructorExceptionType.CONSTRUCTOR_RECORD_ERROR));
    }
}
