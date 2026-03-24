package kr.co.F1FS.app.domain.driver.application.service.record;

import kr.co.F1FS.app.domain.driver.application.port.in.record.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.record.QueryDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.presentation.dto.record.ResponseDriverStandingDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationDriverRecordRelationService implements DriverRecordRelationUseCase {
    private final QueryDriverRecordRelationUseCase queryDriverRecordRelationUseCase;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "DriverStandingList", key = "#racingClassCode", cacheManager = "redisLongCacheManager")
    public List<ResponseDriverStandingDTO> getDriverStandingList(String racingClassCode){
        RacingClass racingClass = RacingClass.valueOf(racingClassCode);

        return queryDriverRecordRelationUseCase.findDriverRecordRelationsByRacingClassAndEntryClassSeasonForDTO(
                racingClass
        );
    }
}
