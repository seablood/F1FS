package kr.co.F1FS.app.presentation.driver.dto;

import kr.co.F1FS.app.presentation.record.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.presentation.record.dto.CreateSinceDebutDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CombinedDriverRequest {
    private CreateDriverDTO driverDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
