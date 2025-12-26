package kr.co.F1FS.app.domain.driver.application.service.driver;

import kr.co.F1FS.app.domain.driver.application.mapper.driver.DriverMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.DriverQueryAggregatorUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.debut.QueryDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.record.QueryDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverQueryAggregatorService implements DriverQueryAggregatorUseCase {
    private final QueryDriverUseCase queryDriverUseCase;
    private final QueryDriverRecordRelationUseCase queryDriverRecordRelationUseCase;
    private final QueryDriverDebutRelationUseCase queryDriverDebutRelationUseCase;
    private final DriverMapper driverMapper;
    private final RecordMapper recordMapper;

    @Override
    public ResponseDriverDTO findByIdForDTO(Long id) {
        Driver driver = queryDriverUseCase.findById(id);
        ResponseCurrentSeasonDTO currentSeasonDTO = recordMapper.toResponseCurrentSeasonDTO(
                queryDriverRecordRelationUseCase.findDriverRecordRelationByDriverInfoAndRacingClass(driver).getCurrentSeason()
        );
        ResponseSinceDebutDTO sinceDebutDTO = recordMapper.toResponseSinceDebutDTO(
                queryDriverDebutRelationUseCase.findDriverDebutRelationByDriverSinceInfoAndRacingClass(driver).getSinceDebut()
        );

        return driverMapper.toResponseDriverDTO(driver, currentSeasonDTO, sinceDebutDTO);
    }
}
