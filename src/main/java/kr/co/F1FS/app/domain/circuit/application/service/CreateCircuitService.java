package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.CreateCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.out.CircuitJpaPort;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.presentation.dto.CreateCircuitCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCircuitService implements CreateCircuitUseCase {
    private final CircuitJpaPort circuitJpaPort;
    private final CircuitDomainService circuitDomainService;
    private final CircuitMapper circuitMapper;
    private final ValidationService validationService;

    @Override
    public ResponseCircuitDTO save(CreateCircuitCommand command) {
        Circuit circuit = circuitDomainService.createEntity(command);
        validationService.checkValid(circuit);

        return circuitMapper.toResponseCircuitDTO(circuitJpaPort.save(circuit));
    }
}
