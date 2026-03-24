package kr.co.F1FS.app.domain.constructor.application.service.record;

import kr.co.F1FS.app.domain.constructor.application.port.in.record.QueryConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryConstructorRecordRelationService implements QueryConstructorRecordRelationUseCase {
    private final ConstructorRecordRelationJpaPort relationJpaPort;

    @Override
    public ConstructorRecordRelation findByConstructor(Long constructorId) {
        return relationJpaPort.findByConstructor(constructorId);
    }

    @Override
    public List<ResponseConstructorStandingDTO> findAllByRacingClassAndEntryClassSeasonForDTO(RacingClass racingClass) {
        return relationJpaPort.findAllByRacingClassAndEntryClassSeason(racingClass);
    }
}
