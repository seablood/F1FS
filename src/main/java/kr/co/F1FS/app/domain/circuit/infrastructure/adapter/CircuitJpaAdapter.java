package kr.co.F1FS.app.domain.circuit.infrastructure.adapter;

import kr.co.F1FS.app.domain.circuit.application.port.out.CircuitJpaPort;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.infrastructure.repository.CircuitRepository;
import kr.co.F1FS.app.global.util.exception.circuit.CircuitException;
import kr.co.F1FS.app.global.util.exception.circuit.CircuitExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CircuitJpaAdapter implements CircuitJpaPort {
    private final CircuitRepository circuitRepository;

    @Override
    public Circuit save(Circuit circuit) {
        return circuitRepository.save(circuit);
    }

    @Override
    public Circuit saveAndFlush(Circuit circuit) {
        return circuitRepository.saveAndFlush(circuit);
    }

    @Override
    public Page<Circuit> findAll(Pageable pageable) {
        return circuitRepository.findAll(pageable);
    }

    @Override
    public Circuit findById(Long id) {
        return circuitRepository.findById(id)
                .orElseThrow(() -> new CircuitException(CircuitExceptionType.CIRCUIT_NOT_FOUND));
    }
}
