package kr.co.F1FS.app.domain.driver.application.mapper;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import org.springframework.stereotype.Component;

@Component
public class DriverRecordRelationMapper {
    public DriverRecordRelation toDriverRecordRelation(Driver driver, CurrentSeason currentSeason){
        return DriverRecordRelation.builder()
                .driver(driver)
                .currentSeason(currentSeason)
                .racingClass(driver.getRacingClass())
                .build();
    }
}
