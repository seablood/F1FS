package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.QueryCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.out.CircuitJpaPort;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryCircuitService implements QueryCircuitUseCase {
    private final CircuitJpaPort circuitJpaPort;
    private final CircuitMapper circuitMapper;

    @Override
    public Page<SimpleResponseCircuitDTO> findAllForDTO(Pageable pageable) {
        return circuitJpaPort.findAll(pageable).map(circuit -> circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }

    @Override
    public Circuit findById(Long id) {
        return circuitJpaPort.findById(id);
    }

    @Override
    public ResponseCircuitDTO findByIdForDTO(Long id) {
        return circuitMapper.toResponseCircuitDTO(circuitJpaPort.findById(id));
    }
}
