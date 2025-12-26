package kr.co.F1FS.app.domain.constructor.application.port.in.constructor;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.constructor.CreateConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;

public interface CreateConstructorUseCase {
    Constructor save(CreateConstructorDTO constructorDTO, CreateCurrentSeasonDTO currentSeasonDTO, CreateSinceDebutDTO sinceDebutDTO);
}
