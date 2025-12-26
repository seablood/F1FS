package kr.co.F1FS.app.domain.driver.application.port.in.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDriverUseCase {
    Driver findById(Long id);
    Driver findByName(String name);
    Driver findByNumber(Integer number);
    Page<SimpleResponseDriverDTO> findAllForSimpleDTO(Pageable pageable);
}
