package kr.co.F1FS.app.domain.admin.driver.application.port.in;

import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.CombinedDriverRequest;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.ModifyDriverDTO;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import org.springframework.data.domain.Page;

public interface AdminDriverUseCase {
    AdminResponseDriverDTO save(CombinedDriverRequest request);
    Page<SimpleResponseDriverDTO> findAll(int page, int size, String condition);
    AdminResponseDriverDTO getDriverById(Long id);
    AdminResponseDriverDTO modify(Long id, ModifyDriverDTO dto);
}
