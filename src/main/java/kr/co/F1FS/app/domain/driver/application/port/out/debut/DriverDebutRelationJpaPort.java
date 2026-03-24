package kr.co.F1FS.app.domain.driver.application.port.out.debut;

import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.global.util.RacingClass;

public interface DriverDebutRelationJpaPort {
    DriverDebutRelation save(DriverDebutRelation debutRelation);
    DriverDebutRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass);
    boolean existsByDriverAndRacingClass(Long driverId, RacingClass racingClass);
}
