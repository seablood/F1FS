package kr.co.F1FS.app.domain.constructor.infrastructure.repository;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructorRecordRelationRepository extends JpaRepository<ConstructorRecordRelation, Long> {
    Optional<ConstructorRecordRelation> findByConstructorInfo(Constructor constructor);
}
