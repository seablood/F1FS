package kr.co.F1FS.app.domain.admin.driver.application.port.out;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.presentation.dto.CreateDriverDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminDriverPort {
    Driver save(Driver driver);
    void saveAndFlush(Driver driver);
    Page<SimpleResponseDriverDTO> findAll(Pageable pageable);
    Driver findById(Long id);
}
