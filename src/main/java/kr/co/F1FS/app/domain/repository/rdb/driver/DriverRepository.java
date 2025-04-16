package kr.co.F1FS.app.domain.repository.rdb.driver;

import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByNumber(Integer number);
    Page<Driver> findAllByRacingClass(RacingClass racingClass, Pageable pageable);
    Page<Driver> findAllByNameContainsIgnoreCaseOrEngNameContainsIgnoreCase(String name, String engName, Pageable pageable);
}
