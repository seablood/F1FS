package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.util.RacingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByNumber(Integer number);
    Page<Driver> findAllByRacingClass(RacingClass racingClass, Pageable pageable);
    Page<Driver> findAllByNameContainsIgnoreCaseOrEngNameContainsIgnoreCase(String name, String engName, Pageable pageable);
}
