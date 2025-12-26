package kr.co.F1FS.app.domain.driver.infrastructure.adapter.debut;

import kr.co.F1FS.app.domain.driver.application.port.out.debut.DriverDebutRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverDebutRelationRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverDebutRelationJpaAdapter implements DriverDebutRelationJpaPort {
    private final DriverDebutRelationRepository relationRepository;

    @Override
    public DriverDebutRelation save(DriverDebutRelation debutRelation) {
        return relationRepository.save(debutRelation);
    }

    @Override
    public DriverDebutRelation findDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationRepository.findDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_DEBUT_ERROR));
    }

    @Override
    public boolean existsDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationRepository.existsDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass);
    }
}
