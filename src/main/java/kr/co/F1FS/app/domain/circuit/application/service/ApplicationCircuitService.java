package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.CircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.in.QueryCircuitUseCase;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationCircuitService implements CircuitUseCase {
    private final QueryCircuitUseCase queryCircuitUseCase;
    private final CircuitMapper circuitMapper;

    @Override
    public Page<SimpleResponseCircuitDTO> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));

        return queryCircuitUseCase.findAll(pageable).map(circuit -> circuitMapper.toSimpleResponseCircuitDTO(circuit));
    }

    @Override
    public Circuit findByIdNotDTONotCache(Long id) {
        return queryCircuitUseCase.findById(id);
    }

    @Override
    @Cacheable(value = "CircuitDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseCircuitDTO getCircuitById(Long id){
        Circuit circuit = queryCircuitUseCase.findById(id);

        return circuitMapper.toResponseCircuitDTO(circuit);
    }

    @Override
    @Cacheable(value = "SimpleCircuitDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public SimpleResponseCircuitDTO getSimpleCircuitById(Long id){
        Circuit circuit = queryCircuitUseCase.findById(id);

        return circuitMapper.toSimpleResponseCircuitDTO(circuit);
    }

    @Override
    public SimpleResponseCircuitDTO toSimpleResponseCircuitDTO(Circuit circuit) {
        return circuitMapper.toSimpleResponseCircuitDTO(circuit);
    }
}
