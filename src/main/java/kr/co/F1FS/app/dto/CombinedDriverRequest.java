package kr.co.F1FS.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CombinedDriverRequest {
    private CreateDriverDTO driverDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
