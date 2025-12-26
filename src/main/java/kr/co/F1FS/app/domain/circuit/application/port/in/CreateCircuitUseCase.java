package kr.co.F1FS.app.domain.circuit.application.port.in;

import kr.co.F1FS.app.domain.circuit.presentation.dto.CreateCircuitCommand;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;

public interface CreateCircuitUseCase {
    ResponseCircuitDTO save(CreateCircuitCommand command);
}
