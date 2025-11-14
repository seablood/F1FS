package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.mapper.CircuitMapper;
import kr.co.F1FS.app.domain.circuit.application.port.in.CircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.out.CircuitJpaPort;
import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.circuit.presentation.dto.CreateCircuitCommand;
import kr.co.F1FS.app.domain.circuit.presentation.dto.ModifyCircuitCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CircuitService implements CircuitUseCase {
    private final CircuitJpaPort circuitJpaPort;
    private final CircuitMapper circuitMapper;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public Circuit createCircuit(CreateCircuitCommand command){
        Circuit circuit = circuitMapper.toCircuit(command);
        validationService.checkValid(circuit);

        return circuitJpaPort.save(circuit);
    }

    @Override
    public Page<SimpleResponseCircuitDTO> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));

        return circuitJpaPort.findAll(pageable);
    }

    @Override
    public Circuit findByIdNotDTONotCache(Long id) {
        return circuitJpaPort.findById(id);
    }

    @Override
    @Cacheable(value = "CircuitDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseCircuitDTO getCircuitById(Long id){
        Circuit circuit = circuitJpaPort.findById(id);

        return circuitMapper.toResponseCircuitDTO(circuit);
    }

    @Override
    @Cacheable(value = "SimpleCircuitDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public SimpleResponseCircuitDTO getSimpleCircuitById(Long id){
        Circuit circuit = circuitJpaPort.findById(id);

        return circuitMapper.toSimpleResponseCircuitDTO(circuit);
    }

    @Override
    public Circuit modify(ModifyCircuitCommand command, Circuit circuit) {
        cacheEvictUtil.evictCachingCircuit(circuit);
        circuit.modify(command);
        validationService.checkValid(circuit);

        return circuitJpaPort.saveAndFlush(circuit);
    }

    @Override
    public ResponseCircuitDTO toResponseCircuitDTO(Circuit circuit) {
        return circuitMapper.toResponseCircuitDTO(circuit);
    }

    @Override
    public SimpleResponseCircuitDTO toSimpleResponseCircuitDTO(Circuit circuit) {
        return circuitMapper.toSimpleResponseCircuitDTO(circuit);
    }
}
