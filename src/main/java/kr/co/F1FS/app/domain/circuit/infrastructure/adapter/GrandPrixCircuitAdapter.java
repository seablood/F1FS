package kr.co.F1FS.app.domain.circuit.infrastructure.adapter;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.infrastructure.repository.CircuitRepository;
import kr.co.F1FS.app.domain.grandprix.application.port.out.GrandPrixCircuitPort;
import kr.co.F1FS.app.global.util.exception.circuit.CircuitException;
import kr.co.F1FS.app.global.util.exception.circuit.CircuitExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrandPrixCircuitAdapter implements GrandPrixCircuitPort {
    private final CircuitRepository circuitRepository;

    @Override
    public Circuit getCircuitByIdNotDTO(Long id) {
        return circuitRepository.findById(id)
                .orElseThrow(() -> new CircuitException(CircuitExceptionType.CIRCUIT_NOT_FOUND));
    }
}
