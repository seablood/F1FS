package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.application.service.DriverDebutRelationService;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationDriverDebutPort;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CDRelationDriverDebutAdapter implements CDRelationDriverDebutPort {
    private final DriverDebutRelationService debutRelationService;

    @Override
    public boolean existsRelation(Driver driver, RacingClass racingClass) {
        return debutRelationService.existsRelation(driver, racingClass);
    }

    @Override
    public void save(Driver driver, SinceDebut sinceDebut) {
        debutRelationService.save(driver, sinceDebut);
    }
}
