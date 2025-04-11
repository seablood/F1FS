package kr.co.F1FS.app.presentation.constructor.dto;

import kr.co.F1FS.app.presentation.record.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.presentation.record.dto.CreateSinceDebutDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CombinedConstructorRequest {
    private CreateConstructorDTO constructorDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
