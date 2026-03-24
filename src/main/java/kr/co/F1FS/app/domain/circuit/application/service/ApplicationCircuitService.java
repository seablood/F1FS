package kr.co.F1FS.app.domain.circuit.application.service;

import kr.co.F1FS.app.domain.circuit.application.port.in.CircuitUseCase;
import kr.co.F1FS.app.domain.circuit.application.port.in.QueryCircuitUseCase;
import kr.co.F1FS.app.global.presentation.dto.circuit.ResponseCircuitDTO;
import kr.co.F1FS.app.global.presentation.dto.circuit.SimpleResponseCircuitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationCircuitService implements CircuitUseCase {
    private final QueryCircuitUseCase queryCircuitUseCase;

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleResponseCircuitDTO> getCircuitAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));

        return queryCircuitUseCase.findAllForDTO(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "CircuitDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseCircuitDTO getCircuitById(Long id){
        return queryCircuitUseCase.findByIdForDTO(id);
    }
}
