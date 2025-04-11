package kr.co.F1FS.app.domain.repository.rdb.driver;

import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.domain.model.rdb.DriverDebutRelation;
import kr.co.F1FS.app.util.RacingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverDebutRelationRepository extends JpaRepository<DriverDebutRelation, Long> {
    boolean existsDriverDebutRelationByDriverSinceInfoAndRacingClass(Driver driver, RacingClass racingClass);
}
