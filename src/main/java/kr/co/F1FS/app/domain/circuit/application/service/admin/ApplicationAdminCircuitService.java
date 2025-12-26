package kr.co.F1FS.app.domain.circuit.application.service.admin;

import kr.co.F1FS.app.domain.circuit.application.mapper.admin.AdminCircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.admin.AdminCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.CreateCircuitDTO;
import kr.co.F1FS.app.domain.circuit.presentation.dto.admin.ModifyCircuitDTO;
import kr.co.F1FS.app.domain.circuit.application.port.in.CreateCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.in.QueryCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.in.UpdateCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminCircuitService implements AdminCircuitUseCase {
    private final CreateCircuitUseCase createCircuitUseCase;
    private final UpdateCircuitUseCase updateCircuitUseCase;
    private final QueryCircuitUseCase queryCircuitUseCase;
    private final AdminCircuitMapper adminCircuitMapper;

    @Override
    @Transactional
    public ResponseCircuitDTO save(CreateCircuitDTO dto) {
        return createCircuitUseCase.save(adminCircuitMapper.toCreateCircuitCommand(dto));
    }

    @Override
    @Transactional
    public ResponseCircuitDTO modify(Long id, ModifyCircuitDTO dto) {
        Circuit circuit = queryCircuitUseCase.findById(id);

        return updateCircuitUseCase.modify(adminCircuitMapper.toModifyCircuitCommand(dto), circuit);
    }
}
