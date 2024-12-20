package kr.co.F1FS.app.service;

import kr.co.F1FS.app.model.*;
import kr.co.F1FS.app.repository.DriverRecordRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRecordRelationService {
    private final DriverRecordRelationRepository relationRepository;

    public void save(Driver driver, CurrentSeason currentSeason){
        DriverRecordRelation relation = DriverRecordRelation.builder()
                .driver(driver)
                .currentSeason(currentSeason)
                .racingClass(driver.getRacingClass())
                .build();

        relationRepository.save(relation);
    }
}
