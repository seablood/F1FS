package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverDebutRelationRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverDebutRelationService {
    private final DriverDebutRelationRepository debutRelationRepository;

    public void save(Driver driver, SinceDebut sinceDebut){
        DriverDebutRelation debutRelation = DriverDebutRelation.builder()
                .driver(driver)
                .sinceDebut(sinceDebut)
                .racingClass(driver.getRacingClass())
                .build();

        debutRelationRepository.save(debutRelation);
    }

    public boolean existsRelation(Driver driver, RacingClass racingClass){
        return debutRelationRepository.existsDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, racingClass);
    }
}
