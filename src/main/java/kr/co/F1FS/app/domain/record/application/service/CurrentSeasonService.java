package kr.co.F1FS.app.domain.record.application.service;

import kr.co.F1FS.app.domain.record.application.port.in.CurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.infrastructure.repository.CurrentSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentSeasonService implements CurrentSeasonUseCase {
    private final CurrentSeasonRepository currentSeasonRepository;

    @Override
    public void updateCurrentSeasonForRace(CurrentSeason currentSeason, int position, int points, boolean isFastestLap) {
        currentSeason.updateChampionshipPoint(points);
        if(position <= 3){
            currentSeason.updatePodiums();
            if(position == 1){
                currentSeason.updateHighestFinish();
            }
        }
        if(isFastestLap) currentSeason.updateFastestLap();

        currentSeasonRepository.saveAndFlush(currentSeason);
    }

    @Override
    public void updateCurrentSeasonForQualifying(CurrentSeason currentSeason, int position) {
        if(position == 1) currentSeason.updatePolePosition();
        currentSeasonRepository.saveAndFlush(currentSeason);
    }

    @Override
    public void updateChampionshipRank(CurrentSeason currentSeason, int championshipRank){
        currentSeason.updateChampionshipRank(championshipRank);
    }
}
