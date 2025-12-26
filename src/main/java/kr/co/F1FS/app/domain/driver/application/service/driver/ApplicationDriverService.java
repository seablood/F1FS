package kr.co.F1FS.app.domain.driver.application.service.driver;

import kr.co.F1FS.app.domain.driver.application.port.in.driver.DriverQueryAggregatorUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.DriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchException;
import kr.co.F1FS.app.global.util.exception.cdSearch.CDSearchExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationDriverService implements DriverUseCase {
    private final QueryDriverUseCase queryDriverUseCase;
    private final DriverQueryAggregatorUseCase aggregatorUseCase;

    @Override
    public Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return queryDriverUseCase.findAllForSimpleDTO(pageable);
    }

    @Override
    @Cacheable(value = "DriverDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseDriverDTO findById(Long id){
        return aggregatorUseCase.findByIdForDTO(id);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "nameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
            case "nameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "name"));
            case "racingClass" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "racingClass"));
            default:
                throw new CDSearchException(CDSearchExceptionType.CONDITION_ERROR_CD);
        }
    }
}
