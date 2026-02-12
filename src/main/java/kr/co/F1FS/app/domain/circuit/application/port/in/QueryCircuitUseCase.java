package kr.co.F1FS.app.domain.circuit.application.port.in;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryCircuitUseCase {
    Page<SimpleResponseCircuitDTO> findAllForDTO(Pageable pageable);
    Circuit findById(Long id);
    ResponseCircuitDTO findByIdForDTO(Long id);
}
