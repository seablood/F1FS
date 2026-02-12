package kr.co.F1FS.app.domain.constructor.application.service.constructor;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.ConstructorQueryAggregatorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationConstructorService implements ConstructorUseCase {
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final ConstructorQueryAggregatorUseCase aggregatorUseCase;

    @Override
    public Page<SimpleResponseConstructorDTO> getConstructorAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return queryConstructorUseCase.findAllForSimpleDTO(pageable);
    }

    @Override
    @Cacheable(value = "ConstructorDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseConstructorDTO getConstructorById(Long id){
        return aggregatorUseCase.findByIdForDTO(id);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "korName"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "korName"));
            case "racingClass" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "racingClass"));
            default:
                throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
        }
    }
}
