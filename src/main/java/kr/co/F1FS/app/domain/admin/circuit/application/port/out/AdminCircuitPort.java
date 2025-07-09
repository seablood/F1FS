package kr.co.F1FS.app.domain.admin.circuit.application.port.out;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;

public interface AdminCircuitPort {
    Circuit save(Circuit circuit);
    void saveAndFlush(Circuit circuit);
    Circuit findByIdNotDTO(Long id);
}
