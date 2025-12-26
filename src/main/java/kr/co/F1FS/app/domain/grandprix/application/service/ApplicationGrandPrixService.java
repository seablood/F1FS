package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.QueryCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.application.port.in.GrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.application.port.in.QueryGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationGrandPrixService implements GrandPrixUseCase {
    private final QueryGrandPrixUseCase queryGrandPrixUseCase;
    private final QueryCircuitUseCase queryCircuitUseCase;
    private final GrandPrixMapper grandPrixMapper;
    private final CircuitMapper circuitMapper;

    @Override
    @Cacheable(value = "GrandPrixList", key = "#season", cacheManager = "redisLongCacheManager")
    public List<SimpleResponseGrandPrixDTO> findAll(Integer season){
        return queryGrandPrixUseCase.findAll(season);
    }

    @Override
    @Cacheable(value = "GrandPrixDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseGrandPrixDTO getGrandPrixById(Long id) {
        GrandPrix grandPrix = queryGrandPrixUseCase.findById(id);
        Circuit circuit = queryCircuitUseCase.findById(grandPrix.getCircuitId());

        return grandPrixMapper.toResponseGrandPrixDTO(grandPrix, circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }
}
