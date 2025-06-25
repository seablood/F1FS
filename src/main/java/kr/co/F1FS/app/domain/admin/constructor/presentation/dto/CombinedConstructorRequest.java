package kr.co.F1FS.app.domain.admin.constructor.presentation.dto;

import kr.co.F1FS.app.domain.constructor.presentation.dto.CreateConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CombinedConstructorRequest {
    private CreateConstructorDTO constructorDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
