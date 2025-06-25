package kr.co.F1FS.app.domain.driver.application.port.in;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.CreateDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.ResponseDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import kr.co.F1FS.app.global.util.RacingClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverUseCase {
    Driver save(CreateDriverDTO driverDTO, CreateCurrentSeasonDTO currentSeasonDTO,
                CreateSinceDebutDTO sinceDebutDTO);
    Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition);
    ResponseDriverDTO findById(Long id);
    Driver findByIdNotDTO(Long id);
    void updateTeam(Driver driver, String constructorName, String constructorEngName);
    void updateRacingClass(Driver driver, RacingClass racingClass);
    ResponseCurrentSeasonDTO getCurrentSeason(Driver driver);
    ResponseSinceDebutDTO getSinceDebut(Driver driver);
    Pageable switchCondition(int page, int size, String condition);
}
