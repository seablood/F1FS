package kr.co.F1FS.app.domain.circuit.application.port.out;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CircuitJpaPort {
    Circuit save(Circuit circuit);
    Circuit saveAndFlush(Circuit circuit);
    Page<Circuit> findAll(Pageable pageable);
    Circuit findById(Long id);
}
