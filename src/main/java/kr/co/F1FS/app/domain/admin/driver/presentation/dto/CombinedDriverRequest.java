package kr.co.F1FS.app.domain.admin.driver.presentation.dto;

import kr.co.F1FS.app.domain.driver.presentation.dto.CreateDriverDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CombinedDriverRequest {
    private CreateDriverDTO driverDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
