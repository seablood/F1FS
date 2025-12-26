package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.port.in.admin.CreateConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateConstructorDriverRelationService implements CreateConstructorDriverRelationUseCase {
    private final CDRelationJpaPort cdRelationJpaPort;
    private final ConstructorDriverRelationDomainService cdDomainService;

    @Override
    public ConstructorDriverRelation save(Constructor constructor, Driver driver) {
        ConstructorDriverRelation relation = cdDomainService.createEntity(constructor, driver);

        return cdRelationJpaPort.save(relation);
    }
}
