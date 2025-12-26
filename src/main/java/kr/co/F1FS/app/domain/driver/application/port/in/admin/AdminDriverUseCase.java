package kr.co.F1FS.app.domain.driver.application.port.in.admin;

import kr.co.F1FS.app.domain.driver.presentation.dto.admin.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.driver.presentation.dto.admin.CombinedDriverRequest;
import kr.co.F1FS.app.domain.driver.presentation.dto.admin.ModifyDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminDriverUseCase {
    AdminResponseDriverDTO save(CombinedDriverRequest request);
    Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition);
    AdminResponseDriverDTO getDriverById(Long id);
    AdminResponseDriverDTO modify(Long id, ModifyDriverDTO dto);
    Pageable switchCondition(int page, int size, String condition);
}
