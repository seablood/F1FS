package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.team.application.port.in.admin.UpdateConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateConstructorDriverRelationService implements UpdateConstructorDriverRelationUseCase {
    private final CDRelationJpaPort cdRelationJpaPort;
    private final ConstructorDriverRelationDomainService relationDomainService;

    @Override
    public void updateConstructor(ConstructorDriverRelation relation, Constructor constructor) {
        relationDomainService.updateConstructor(relation, constructor);

        cdRelationJpaPort.saveAndFlush(relation);
    }
}
