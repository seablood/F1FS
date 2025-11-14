package kr.co.F1FS.app.domain.admin.grandprix.application.service;

import kr.co.F1FS.app.domain.admin.grandprix.application.mapper.AdminGrandPrixMapper;
import kr.co.F1FS.app.domain.admin.grandprix.application.port.in.AdminGrandPrixUseCase;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.ModifyGrandPrixDTO;
import kr.co.F1FS.app.domain.circuit.application.port.in.CircuitUseCase;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.elastic.application.port.in.GrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.grandprix.application.port.in.GrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminGrandPrixService implements AdminGrandPrixUseCase {
    private final GrandPrixUseCase grandPrixUseCase;
    private final GrandPrixSearchUseCase searchUseCase;
    private final SessionUseCase sessionUseCase;
    private final CircuitUseCase circuitUseCase;
    private final AdminGrandPrixMapper adminGrandPrixMapper;

    @Override
    @Transactional
    public ResponseGrandPrixDTO save(CreateGrandPrixDTO dto) {
        GrandPrix grandPrix = grandPrixUseCase.createGrandPrix(adminGrandPrixMapper.toCreateGrandPrixCommand(dto));
        Circuit circuit = circuitUseCase.findByIdNotDTONotCache(grandPrix.getCircuitId());
        sessionUseCase.save(grandPrix);
        grandPrix = grandPrixUseCase.save(grandPrix);
        searchUseCase.save(grandPrix);

        return grandPrixUseCase.toResponseGrandPrixDTO(grandPrix, circuitUseCase.toSimpleResponseCircuitDTO(circuit));
    }

    @Override
    public List<SimpleResponseGrandPrixDTO> findAll(Integer season){
        return grandPrixUseCase.findAll(season);
    }

    @Override
    public ResponseGrandPrixDTO getGrandPrixById(Long id){
        return grandPrixUseCase.getGrandPrixById(id);
    }

    @Override
    @Transactional
    public ResponseGrandPrixDTO modify(ModifyGrandPrixDTO dto, Long id) {
        GrandPrix grandPrix = grandPrixUseCase.findByIdNotDTONotCache(id);
        Circuit circuit = circuitUseCase.findByIdNotDTONotCache(grandPrix.getCircuitId());
        GrandPrixDocument document = searchUseCase.findById(id);

        grandPrix = grandPrixUseCase.modify(adminGrandPrixMapper.toModifyGrandPrixCommand(dto), grandPrix);
        searchUseCase.modify(document, grandPrix);
        grandPrixUseCase.saveAndFlush(grandPrix);
        searchUseCase.save(document);

        return grandPrixUseCase.toResponseGrandPrixDTO(grandPrix, circuitUseCase.toSimpleResponseCircuitDTO(circuit));
    }
}
