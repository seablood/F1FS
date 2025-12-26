package kr.co.F1FS.app.domain.driver.application.service.debut;

import kr.co.F1FS.app.domain.driver.application.port.in.debut.CreateDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.debut.DriverDebutRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDriverDebutRelationService implements CreateDriverDebutRelationUseCase {
    private final DriverDebutRelationJpaPort relationJpaPort;
    private final DriverDebutRelationDomainService relationDomainService;

    @Override
    public void save(Driver driver, SinceDebut sinceDebut) {
        DriverDebutRelation relation = relationDomainService.createEntity(driver, sinceDebut);

        relationJpaPort.save(relation);
    }
}
