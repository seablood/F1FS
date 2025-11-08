package kr.co.F1FS.app.domain.circuit.application.port.in;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.presentation.dto.CreateCircuitCommand;
import kr.co.F1FS.app.domain.circuit.presentation.dto.ModifyCircuitCommand;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import org.springframework.data.domain.Page;

public interface CircuitUseCase {
    Circuit createCircuit(CreateCircuitCommand command);
    Page<SimpleResponseCircuitDTO> findAll(int page, int size);
    Circuit findById(Long id);
    ResponseCircuitDTO getCircuitById(Long id);
    SimpleResponseCircuitDTO getSimpleCircuitById(Long id);
    Circuit modify(ModifyCircuitCommand command, Circuit circuit);
    ResponseCircuitDTO toResponseCircuitDTO(Circuit circuit);
    SimpleResponseCircuitDTO toSimpleResponseCircuitDTO(Circuit circuit);
}
