package kr.co.F1FS.app.domain.driver.application.port.in.driver;

import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverUseCase {
    Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition);
    ResponseDriverDTO findById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
