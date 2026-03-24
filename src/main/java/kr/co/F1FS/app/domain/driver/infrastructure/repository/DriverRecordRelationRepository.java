package kr.co.F1FS.app.domain.driver.infrastructure.repository;

import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRecordRelationRepository extends JpaRepository<DriverRecordRelation, Long> {
}
