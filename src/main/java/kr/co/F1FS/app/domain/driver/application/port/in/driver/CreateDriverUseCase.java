package kr.co.F1FS.app.domain.driver.application.port.in.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.driver.CreateDriverDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;

public interface CreateDriverUseCase {
    Driver save(CreateDriverDTO driverDTO, CreateCurrentSeasonDTO currentSeasonDTO, CreateSinceDebutDTO sinceDebutDTO);
}
