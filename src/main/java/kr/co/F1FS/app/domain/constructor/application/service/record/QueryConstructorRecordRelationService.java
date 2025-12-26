package kr.co.F1FS.app.domain.constructor.application.service.record;

import kr.co.F1FS.app.domain.constructor.application.port.in.record.QueryConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.out.record.ConstructorRecordRelationJpaPort;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryConstructorRecordRelationService implements QueryConstructorRecordRelationUseCase {
    private final ConstructorRecordRelationJpaPort relationJpaPort;

    @Override
    public ConstructorRecordRelation findByConstructor(Constructor constructor) {
        return relationJpaPort.findByConstructorInfo(constructor);
    }
}
