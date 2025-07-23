package kr.co.F1FS.app.domain.admin.grandprix.application.service;

import kr.co.F1FS.app.domain.admin.grandprix.application.mapper.AdminGrandPrixMapper;
import kr.co.F1FS.app.domain.admin.grandprix.application.port.in.AdminGrandPrixUseCase;
import kr.co.F1FS.app.domain.admin.grandprix.application.port.out.AdminGrandPrixCircuitPort;
import kr.co.F1FS.app.domain.admin.grandprix.application.port.out.AdminGrandPrixPort;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.ModifyGrandPrixDTO;
import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.in.GrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminGrandPrixService implements AdminGrandPrixUseCase {
    private final GrandPrixUseCase grandPrixUseCase;
    private final SessionUseCase sessionUseCase;
    private final AdminGrandPrixPort grandPrixPort;
    private final AdminGrandPrixCircuitPort circuitPort;
    private final AdminGrandPrixMapper adminGrandPrixMapper;
    private final GrandPrixMapper grandPrixMapper;
    private final CircuitMapper circuitMapper;

    @Override
    @Transactional
    public ResponseGrandPrixDTO save(CreateGrandPrixDTO dto) {
        GrandPrix grandPrix = grandPrixUseCase.createGrandPrix(adminGrandPrixMapper.toCreateGrandPrixCommand(dto));
        Circuit circuit = circuitPort.getCircuitByIdNotDTO(grandPrix.getCircuitId());
        sessionUseCase.save(grandPrix);
        grandPrixPort.save(grandPrix);

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }

    @Override
    @Transactional
    public ResponseGrandPrixDTO modify(ModifyGrandPrixDTO dto, Long id) {
        GrandPrix grandPrix = grandPrixPort.getGrandPrixById(id);
        Circuit circuit = circuitPort.getCircuitByIdNotDTO(grandPrix.getCircuitId());
        grandPrix = grandPrixUseCase.modify(adminGrandPrixMapper.toModifyGrandPrixCommand(dto), grandPrix);
        grandPrixPort.saveAndFlush(grandPrix);

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }
}
