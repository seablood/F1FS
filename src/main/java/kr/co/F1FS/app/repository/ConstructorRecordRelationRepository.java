package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.ConstructorRecordRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructorRecordRelationRepository extends JpaRepository<ConstructorRecordRelation, Long> {
    Optional<ConstructorRecordRelation> findByConstructorInfo(Constructor constructor);
}
