package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.DriverDebutRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverDebutRelationRepository extends JpaRepository<DriverDebutRelation, Long> {
    Optional<DriverDebutRelation> findByDriverSinceInfo(Driver driver);
}
