package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.presentation.dto.CreateCircuitCommand;
import kr.co.F1FS.app.domain.circuit.presentation.dto.ModifyCircuitCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CircuitDomainService {
    private final CircuitMapper circuitMapper;

    public Circuit createEntity(CreateCircuitCommand command){
        return circuitMapper.toCircuit(command);
    }

    public void modify(ModifyCircuitCommand command, Circuit circuit){
        circuit.modify(command);
    }
}
