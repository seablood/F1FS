package kr.co.F1FS.app.domain.circuit.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.circuit.application.port.out.AdminCircuitPort;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.infrastructure.repository.CircuitRepository;
import kr.co.F1FS.app.global.util.exception.circuit.CircuitException;
import kr.co.F1FS.app.global.util.exception.circuit.CircuitExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCircuitAdapter implements AdminCircuitPort {
    private final CircuitRepository circuitRepository;

    @Override
    public Circuit save(Circuit circuit) {
        return circuitRepository.save(circuit);
    }

    @Override
    public void saveAndFlush(Circuit circuit) {
        circuitRepository.saveAndFlush(circuit);
    }

    @Override
    public Circuit findByIdNotDTO(Long id) {
        return circuitRepository.findById(id)
                .orElseThrow(() -> new CircuitException(CircuitExceptionType.CIRCUIT_NOT_FOUND));
    }
}
