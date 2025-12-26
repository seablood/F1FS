package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.UpdateCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.out.CircuitJpaPort;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.presentation.dto.ModifyCircuitCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCircuitService implements UpdateCircuitUseCase {
    private final CircuitJpaPort circuitJpaPort;
    private final CircuitDomainService circuitDomainService;
    private final CircuitMapper circuitMapper;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public ResponseCircuitDTO modify(ModifyCircuitCommand command, Circuit circuit) {
        cacheEvictUtil.evictCachingCircuit(circuit);
        circuitDomainService.modify(command, circuit);
        validationService.checkValid(circuit);

        return circuitMapper.toResponseCircuitDTO(circuitJpaPort.saveAndFlush(circuit));
    }
}
