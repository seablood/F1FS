package kr.co.F1FS.app.domain.repository.rdb.driver;

import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.domain.model.rdb.DriverRecordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRecordRelationRepository extends JpaRepository<DriverRecordRelation, Long> {
    List<DriverRecordRelation> findAllByDriverInfo(Driver driver);
}
