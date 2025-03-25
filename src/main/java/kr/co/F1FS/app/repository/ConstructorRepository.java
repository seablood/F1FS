package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.util.RacingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConstructorRepository extends JpaRepository<Constructor, Long> {
    Optional<Constructor> findByName(String name);
    Page<Constructor> findAllByRacingClass(RacingClass racingClass, Pageable pageable);
    Page<Constructor> findAllByNameContainsIgnoreCaseOrEngNameContainsIgnoreCase(String name, String engName,
                                                                                 Pageable pageable);
}
