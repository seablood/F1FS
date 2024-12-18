package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.ConstructorDriverRelation;
import kr.co.F1FS.app.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructorDriverRelationRepository extends JpaRepository<ConstructorDriverRelation, Long> {
    ConstructorDriverRelation findConstructorDriverRelationByDriver(Driver driver);
}
