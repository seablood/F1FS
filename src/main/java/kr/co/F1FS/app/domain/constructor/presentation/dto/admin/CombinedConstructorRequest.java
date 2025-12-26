package kr.co.F1FS.app.domain.constructor.presentation.dto.admin;

import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.CreateConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CombinedConstructorRequest {
    private CreateConstructorDTO constructorDTO;
    private CreateCurrentSeasonDTO currentSeasonDTO;
    private CreateSinceDebutDTO sinceDebutDTO;
}
