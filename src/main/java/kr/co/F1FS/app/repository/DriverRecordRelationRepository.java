package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.model.DriverRecordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRecordRelationRepository extends JpaRepository<DriverRecordRelation, Long> {
    List<DriverRecordRelation> findAllByDriverInfo(Driver driver);
}
