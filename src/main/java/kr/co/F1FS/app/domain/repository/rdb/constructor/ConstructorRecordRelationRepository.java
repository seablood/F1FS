package kr.co.F1FS.app.domain.repository.rdb.constructor;

import kr.co.F1FS.app.domain.model.rdb.Constructor;
import kr.co.F1FS.app.domain.model.rdb.ConstructorRecordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructorRecordRelationRepository extends JpaRepository<ConstructorRecordRelation, Long> {
    Optional<ConstructorRecordRelation> findByConstructorInfo(Constructor constructor);
}
