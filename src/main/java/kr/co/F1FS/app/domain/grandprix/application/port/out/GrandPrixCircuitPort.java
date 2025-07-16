package kr.co.F1FS.app.domain.grandprix.application.port.out;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;

public interface GrandPrixCircuitPort {
    Circuit getCircuitByIdNotDTO(Long id);
}
