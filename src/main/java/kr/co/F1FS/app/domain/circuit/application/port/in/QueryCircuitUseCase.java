package kr.co.F1FS.app.domain.circuit.application.port.in;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryCircuitUseCase {
    Page<Circuit> findAll(Pageable pageable);
    Circuit findById(Long id);
}
