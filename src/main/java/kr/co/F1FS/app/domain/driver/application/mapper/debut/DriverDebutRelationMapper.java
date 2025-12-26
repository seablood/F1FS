package kr.co.F1FS.app.domain.driver.application.mapper.debut;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import org.springframework.stereotype.Component;

@Component
public class DriverDebutRelationMapper {
    public DriverDebutRelation toDriverDebutRelation(Driver driver, SinceDebut sinceDebut){
        return DriverDebutRelation.builder()
                .driver(driver)
                .sinceDebut(sinceDebut)
                .racingClass(driver.getRacingClass())
                .build();
    }
}
