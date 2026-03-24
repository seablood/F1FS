package kr.co.F1FS.app.domain.team.infrastructure.repository;

import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructorDriverRelationRepository extends JpaRepository<ConstructorDriverRelation, Long> {
}
