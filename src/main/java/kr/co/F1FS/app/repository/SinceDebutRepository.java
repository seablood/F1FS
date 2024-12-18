package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.SinceDebut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinceDebutRepository extends JpaRepository<SinceDebut, Long> {
}
