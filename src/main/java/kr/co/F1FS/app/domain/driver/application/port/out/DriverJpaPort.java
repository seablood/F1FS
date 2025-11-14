package kr.co.F1FS.app.domain.driver.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverJpaPort {
    Driver save(Driver driver);
    Driver saveAndFlush(Driver driver);
    Page<SimpleResponseDriverDTO> findAll(Pageable pageable);
    Driver findById(Long id);
    Driver findByName(String name);
    Driver findByEngName(String engName);
    Driver findByNumber(Integer number);
}
