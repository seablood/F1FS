package kr.co.F1FS.app.domain.team.infrastructure.repository;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConstructorDriverRelationRepository extends JpaRepository<ConstructorDriverRelation, Long> {
    List<ConstructorDriverRelation> findConstructorDriverRelationByDriver(Driver driver);
    Optional<ConstructorDriverRelation> findConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass);
    boolean existsConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass);
    boolean existsConstructorDriverRelationByDriverAndConstructor(Driver driver, Constructor constructor);
}
