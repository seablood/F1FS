package kr.co.F1FS.app.domain.driver.infrastructure.repository;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.global.util.RacingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRecordRelationRepository extends JpaRepository<DriverRecordRelation, Long> {
    Optional<DriverRecordRelation> findDriverRecordRelationByDriverInfoAndRacingClass(Driver driver, RacingClass racingClass);
}
