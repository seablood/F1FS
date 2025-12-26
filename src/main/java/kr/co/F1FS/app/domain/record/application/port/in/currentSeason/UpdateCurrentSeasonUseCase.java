package kr.co.F1FS.app.domain.record.application.port.in.currentSeason;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;

public interface UpdateCurrentSeasonUseCase {
    void updateCurrentSeasonForRace(CurrentSeason currentSeason, int position, int points, boolean isFastestLap);
    void updateCurrentSeasonForQualifying(CurrentSeason currentSeason, int position);
    void updateChampionshipRank(CurrentSeason currentSeason, int championshipRank);
}
