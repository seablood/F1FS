package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.driver.application.mapper.DriverDebutRelationMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverDebutRelationJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverDebutRelationService implements DriverDebutRelationUseCase {
    private final DriverDebutRelationMapper relationMapper;
    private final DriverDebutRelationJpaPort relationJpaPort;

    @Override
    public void save(Driver driver, SinceDebut sinceDebut){
        DriverDebutRelation debutRelation = relationMapper.toDriverDebutRelation(driver, sinceDebut);

        relationJpaPort.save(debutRelation);
    }

    @Override
    public boolean existsDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationJpaPort.existsDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass);
    }

    @Override
    public DriverDebutRelation getSinceDebutByDriverAndRacingClass(Driver driver){
        return relationJpaPort.findDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, driver.getRacingClass());
    }
}
