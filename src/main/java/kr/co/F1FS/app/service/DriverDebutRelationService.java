package kr.co.F1FS.app.service;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.DriverDebutRelation;
import kr.co.F1FS.app.model.SinceDebut;
import kr.co.F1FS.app.repository.DriverDebutRelationRepository;
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
}
