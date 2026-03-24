package kr.co.F1FS.app.domain.constructor.infrastructure.repository;

import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructorRecordRelationRepository extends JpaRepository<ConstructorRecordRelation, Long> {
}
