package kr.co.F1FS.app.domain.constructor.application.service.record;

import kr.co.F1FS.app.domain.constructor.application.port.in.record.CreateConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateConstructorRecordRelationService implements CreateConstructorRecordRelationUseCase {
    private final ConstructorRecordRelationJpaPort relationJpaPort;
    private final ConstructorRecordRelationDomainService relationDomainService;

    @Override
    public void save(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut) {
        ConstructorRecordRelation relation = relationDomainService.createEntity(constructor, currentSeason, sinceDebut);

        relationJpaPort.save(relation);
    }
}
