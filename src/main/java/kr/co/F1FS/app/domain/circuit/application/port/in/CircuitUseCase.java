package kr.co.F1FS.app.domain.circuit.application.port.in;

import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import org.springframework.data.domain.Page;

public interface CircuitUseCase {
    Page<SimpleResponseCircuitDTO> getCircuitAll(int page, int size);
    ResponseCircuitDTO getCircuitById(Long id);
}
