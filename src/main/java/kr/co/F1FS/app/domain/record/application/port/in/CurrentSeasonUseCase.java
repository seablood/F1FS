package kr.co.F1FS.app.domain.record.application.port.in;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;

public interface CurrentSeasonUseCase {
    CurrentSeason save(CurrentSeason currentSeason);
    CurrentSeason saveAndFlush(CurrentSeason currentSeason);
    void updateCurrentSeasonForRace(CurrentSeason currentSeason, int position, int points, boolean isFastestLap);
    void updateCurrentSeasonForQualifying(CurrentSeason currentSeason, int position);
    void updateChampionshipRank(CurrentSeason currentSeason, int championshipRank);
    CurrentSeason toCurrentSeason(CreateCurrentSeasonDTO dto);
    ResponseCurrentSeasonDTO toResponseCurrentSeasonDTO(CurrentSeason currentSeason);
}
