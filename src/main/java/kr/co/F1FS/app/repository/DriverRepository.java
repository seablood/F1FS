package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.util.RacingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByRacingClass(RacingClass racingClass);
}
