package kr.co.F1FS.app.domain.admin.grandprix.application.port.out;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;

public interface AdminGrandPrixCircuitPort {
    Circuit getCircuitByIdNotDTO(Long id);
}
