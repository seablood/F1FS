package kr.co.F1FS.app.domain.record.application.port.in;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;

public interface CurrentSeasonUseCase {
    void updateCurrentSeasonForRace(CurrentSeason currentSeason, int position, int points, boolean isFastestLap);
    void updateCurrentSeasonForQualifying(CurrentSeason currentSeason, int position);
}
