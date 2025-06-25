package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRecordRelationRepository;
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
