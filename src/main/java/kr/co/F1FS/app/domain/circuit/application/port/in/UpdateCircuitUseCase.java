package kr.co.F1FS.app.domain.circuit.application.port.in;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.presentation.dto.ModifyCircuitCommand;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;

public interface UpdateCircuitUseCase {
    ResponseCircuitDTO modify(ModifyCircuitCommand command, Circuit circuit);
}
