package kr.co.F1FS.app.domain.driver.presentation.dto.admin;

import kr.co.F1FS.app.domain.driver.presentation.dto.driver.CreateDriverDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CombinedDriverRequest {
    private CreateDriverDTO driverDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
