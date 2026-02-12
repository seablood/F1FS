package kr.co.F1FS.app.domain.constructor.application.service.record;

import kr.co.F1FS.app.domain.constructor.application.mapper.record.ConstructorRecordRelationMapper;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.QueryConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
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
    private final ConstructorRecordRelationMapper relationMapper;

    @Override
    public ConstructorRecordRelation findByConstructor(Constructor constructor) {
        return relationJpaPort.findByConstructorInfo(constructor);
    }

    @Override
    public List<ResponseConstructorStandingDTO> findConstructorRecordRelationsByRacingClassAndEntryClassSeasonForDTO(RacingClass racingClass) {
        List<ConstructorRecordRelation> relationList =
                relationJpaPort.findConstructorRecordRelationsByRacingClassAndEntryClassSeason(racingClass, true);

        return relationList.stream()
                .map(relation -> relationMapper.toResponseConstructorStandingDTO(relation))
                .sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()))
                .toList();
    }
}
