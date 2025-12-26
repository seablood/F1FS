package kr.co.F1FS.app.domain.driver.application.service.debut;

import kr.co.F1FS.app.domain.driver.application.port.in.debut.CheckDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.debut.DriverDebutRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckDriverDebutRelationService implements CheckDriverDebutRelationUseCase {
    private final DriverDebutRelationJpaPort relationJpaPort;

    @Override
    public boolean existsDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationJpaPort.existsDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass);
    }
}
