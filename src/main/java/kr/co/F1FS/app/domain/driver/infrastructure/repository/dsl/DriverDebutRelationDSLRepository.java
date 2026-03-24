package kr.co.F1FS.app.domain.driver.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.Optional;

public interface DriverDebutRelationDSLRepository {
    Optional<DriverDebutRelation> findByDriverAndRacingClass(Long driverId, RacingClass racingClass);
    boolean existsByDriverAndRacingClass(Long driverId, RacingClass racingClass);
}
