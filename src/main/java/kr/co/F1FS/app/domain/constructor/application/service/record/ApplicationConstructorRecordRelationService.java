package kr.co.F1FS.app.domain.constructor.application.service.record;

import kr.co.F1FS.app.domain.constructor.application.port.in.record.ConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.QueryConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationConstructorRecordRelationService implements ConstructorRecordRelationUseCase {
    private final QueryConstructorRecordRelationUseCase queryConstructorRecordRelationUseCase;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "ConstructorStandingList", key = "#racingClassCode", cacheManager = "redisLongCacheManager")
    public List<ResponseConstructorStandingDTO> getConstructorStandingList(String racingClassCode){
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);

        return queryConstructorRecordRelationUseCase.findAllByRacingClassAndEntryClassSeasonForDTO(racingClass);
    }
}
