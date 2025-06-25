package kr.co.F1FS.app.domain.admin.driver.application.port.in;

import kr.co.F1FS.app.domain.admin.driver.presentation.dto.AdminResponseDriverDTO;
import kr.co.F1FS.app.domain.admin.driver.presentation.dto.CombinedDriverRequest;

public interface AdminDriverUseCase {
    AdminResponseDriverDTO save(CombinedDriverRequest request);
}
