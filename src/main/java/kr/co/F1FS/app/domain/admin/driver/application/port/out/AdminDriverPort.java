package kr.co.F1FS.app.domain.admin.driver.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.CreateDriverDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;

public interface AdminDriverPort {
    Driver save(Driver driver);
    void saveAndFlush(Driver driver);
    Driver findById(Long id);
}
