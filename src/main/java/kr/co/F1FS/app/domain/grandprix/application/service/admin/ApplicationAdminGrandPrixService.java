package kr.co.F1FS.app.domain.grandprix.application.service.admin;

import kr.co.F1FS.app.domain.grandprix.application.mapper.admin.AdminGrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.in.admin.AdminGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.admin.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.admin.ModifyGrandPrixDTO;
import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.QueryCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.CreateGrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.QueryGrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix.UpdateGrandPrixSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.GrandPrixDocument;
import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.in.CreateGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.in.QueryGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.in.UpdateGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.session.application.port.in.CreateSessionUseCase;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationAdminGrandPrixService implements AdminGrandPrixUseCase {
    private final CreateGrandPrixUseCase createGrandPrixUseCase;
    private final UpdateGrandPrixUseCase updateGrandPrixUseCase;
    private final QueryGrandPrixUseCase queryGrandPrixUseCase;
    private final CreateGrandPrixSearchUseCase createGrandPrixSearchUseCase;
    private final UpdateGrandPrixSearchUseCase updateGrandPrixSearchUseCase;
    private final QueryGrandPrixSearchUseCase queryGrandPrixSearchUseCase;
    private final CreateSessionUseCase createSessionUseCase;
    private final QueryCircuitUseCase queryCircuitUseCase;
    private final AdminGrandPrixMapper adminGrandPrixMapper;
    private final GrandPrixMapper grandPrixMapper;
    private final CircuitMapper circuitMapper;

    @Override
    @Transactional
    public ResponseGrandPrixDTO save(CreateGrandPrixDTO dto) {
        GrandPrix grandPrix = createGrandPrixUseCase.save(adminGrandPrixMapper.toCreateGrandPrixCommand(dto));
        Circuit circuit = queryCircuitUseCase.findById(grandPrix.getCircuitId());
        createSessionUseCase.save(grandPrix);
        createGrandPrixSearchUseCase.save(grandPrix);

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }

    @Override
    @Cacheable(value = "GrandPrixListForAdmin", key = "#season", cacheManager = "redisLongCacheManager")
    public List<SimpleResponseGrandPrixDTO> findAll(Integer season){
        return queryGrandPrixUseCase.findAll(season);
    }

    @Override
    @Cacheable(value = "GrandPrixDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseGrandPrixDTO getGrandPrixById(Long id){
        GrandPrix grandPrix = queryGrandPrixUseCase.findById(id);
        Circuit circuit = queryCircuitUseCase.findById(grandPrix.getCircuitId());

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }

    @Override
    @Transactional
    public ResponseGrandPrixDTO modify(ModifyGrandPrixDTO dto, Long id) {
        GrandPrix grandPrix = queryGrandPrixUseCase.findById(id);
        Circuit circuit = queryCircuitUseCase.findById(grandPrix.getCircuitId());
        GrandPrixDocument document = queryGrandPrixSearchUseCase.findById(id);

        updateGrandPrixUseCase.modify(adminGrandPrixMapper.toModifyGrandPrixCommand(dto), grandPrix);
        updateGrandPrixSearchUseCase.modify(document, grandPrix);

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }
}
