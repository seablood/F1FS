package kr.co.F1FS.app.domain.record.application.port.in.currentSeason;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;

public interface CreateCurrentSeasonUseCase {
    CurrentSeason save(CreateCurrentSeasonDTO currentSeasonDTO);
    CurrentSeason save();
}
