package kr.co.F1FS.app.domain.admin.circuit.application.port.in;

import kr.co.F1FS.app.domain.admin.circuit.presentation.dto.CreateCircuitDTO;
import kr.co.F1FS.app.domain.admin.circuit.presentation.dto.ModifyCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;

public interface AdminCircuitUseCase {
    ResponseCircuitDTO save(CreateCircuitDTO dto);
    ResponseCircuitDTO modify(Long id, ModifyCircuitDTO dto);
}
