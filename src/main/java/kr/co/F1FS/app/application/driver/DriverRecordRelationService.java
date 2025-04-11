package kr.co.F1FS.app.application.driver;

import kr.co.F1FS.app.domain.model.rdb.CurrentSeason;
import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.domain.model.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.repository.rdb.driver.DriverRecordRelationRepository;
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
