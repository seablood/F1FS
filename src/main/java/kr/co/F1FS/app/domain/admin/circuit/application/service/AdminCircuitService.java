package kr.co.F1FS.app.domain.admin.circuit.application.service;

import kr.co.F1FS.app.domain.admin.circuit.application.mapper.AdminCircuitMapper;
import kr.co.F1FS.app.domain.admin.circuit.application.port.in.AdminCircuitUseCase;
import kr.co.F1FS.app.domain.admin.circuit.presentation.dto.CreateCircuitDTO;
import kr.co.F1FS.app.domain.admin.circuit.presentation.dto.ModifyCircuitDTO;
import kr.co.F1FS.app.domain.circuit.application.port.in.CircuitUseCase;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCircuitService implements AdminCircuitUseCase {
    private final CircuitUseCase circuitUseCase;
    private final AdminCircuitMapper adminCircuitMapper;

    @Override
    @Transactional
    public ResponseCircuitDTO save(CreateCircuitDTO dto) {
        Circuit circuit = circuitUseCase.createCircuit(adminCircuitMapper
                .toCreateCircuitCommand(dto));

        return circuitUseCase.toResponseCircuitDTO(circuit);
    }

    @Override
    @Transactional
    public ResponseCircuitDTO modify(Long id, ModifyCircuitDTO dto) {
        Circuit circuit = circuitUseCase.findById(id);
        circuit = circuitUseCase.modify(adminCircuitMapper.toModifyCircuitCommand(dto), circuit);

        return circuitUseCase.toResponseCircuitDTO(circuit);
    }
}
