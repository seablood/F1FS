package kr.co.F1FS.app.domain.driver.infrastructure.repository;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByNumber(Integer number);
    Page<Driver> findAll(Pageable pageable);
}
