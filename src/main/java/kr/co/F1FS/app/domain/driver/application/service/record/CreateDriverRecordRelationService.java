package kr.co.F1FS.app.domain.driver.application.service.record;

import kr.co.F1FS.app.domain.driver.application.port.in.record.CreateDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.record.DriverRecordRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDriverRecordRelationService implements CreateDriverRecordRelationUseCase {
    private final DriverRecordRelationJpaPort relationJpaPort;
    private final DriverRecordRelationDomainService relationDomainService;

    @Override
    public void save(Driver driver, CurrentSeason currentSeason) {
        DriverRecordRelation relation = relationDomainService.createEntity(driver, currentSeason);

        relationJpaPort.save(relation);
    }
}
