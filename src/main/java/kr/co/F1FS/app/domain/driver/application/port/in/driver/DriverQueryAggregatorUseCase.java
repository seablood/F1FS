package kr.co.F1FS.app.domain.driver.application.port.in.driver;

import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;

public interface DriverQueryAggregatorUseCase {
    ResponseDriverDTO findByIdForDTO(Long id);
}
