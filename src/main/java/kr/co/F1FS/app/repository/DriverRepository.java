package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.util.RacingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByName(String name);
    Optional<Driver> findByNumber(Integer number);
    List<Driver> findAllByRacingClass(RacingClass racingClass);
    List<Driver> findAllByNameContainsIgnoreCaseOrEngNameContainsIgnoreCase(String name, String engName);
}
