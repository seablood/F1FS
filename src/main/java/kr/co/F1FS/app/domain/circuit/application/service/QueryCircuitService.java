package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.port.in.QueryCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.out.CircuitJpaPort;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryCircuitService implements QueryCircuitUseCase {
    private final CircuitJpaPort circuitJpaPort;

    @Override
    public Page<Circuit> findAll(Pageable pageable) {
        return circuitJpaPort.findAll(pageable);
    }

    @Override
    public Circuit findById(Long id) {
        return circuitJpaPort.findById(id);
    }
}
