package kr.co.F1FS.app.domain.driver.application.service.debut;

import kr.co.F1FS.app.domain.driver.application.port.in.debut.QueryDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.debut.DriverDebutRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryDriverDebutRelationService implements QueryDriverDebutRelationUseCase {
    private final DriverDebutRelationJpaPort relationJpaPort;

    @Override
    public DriverDebutRelation findDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver) {
        return relationJpaPort.findDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, driver.getRacingClass());
    }
}
