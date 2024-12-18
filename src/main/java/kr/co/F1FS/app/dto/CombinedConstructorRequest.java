package kr.co.F1FS.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CombinedConstructorRequest {
    private CreateConstructorDTO constructorDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
