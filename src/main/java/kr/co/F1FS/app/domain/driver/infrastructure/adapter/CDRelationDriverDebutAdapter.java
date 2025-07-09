package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverDebutRelationRepository;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationDriverDebutPort;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CDRelationDriverDebutAdapter implements CDRelationDriverDebutPort {
    private final DriverDebutRelationRepository debutRelationRepository;

    @Override
    public boolean existsRelation(Driver driver, RacingClass racingClass) {
        return debutRelationRepository.existsDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass);
    }
}
