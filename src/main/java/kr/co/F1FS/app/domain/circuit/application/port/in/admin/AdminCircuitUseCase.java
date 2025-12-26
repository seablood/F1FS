package kr.co.F1FS.app.domain.circuit.application.port.in.admin;

import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.CreateCircuitDTO;
import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.ModifyCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;

public interface AdminCircuitUseCase {
    ResponseCircuitDTO save(CreateCircuitDTO dto);
    ResponseCircuitDTO modify(Long id, ModifyCircuitDTO dto);
}
