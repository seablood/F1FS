package kr.co.F1FS.app.domain.record.application.service.currentSeason;

import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.UpdateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.out.currentSeason.CurrentSeasonJpaPort;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCurrentSeasonService implements UpdateCurrentSeasonUseCase {
    private final CurrentSeasonJpaPort currentSeasonJpaPort;
    private final CurrentSeasonDomainService currentSeasonDomainService;

    @Override
    public void updateCurrentSeasonForRace(CurrentSeason currentSeason, int position, int points, boolean isFastestLap) {
        currentSeasonDomainService.updateChampionshipPoint(currentSeason, points);
        if(position <= 3){
            currentSeasonDomainService.updatePodiums(currentSeason);
            if(position == 1){
                currentSeasonDomainService.updateHighestFinish(currentSeason);
            }
        }
        if(isFastestLap) currentSeasonDomainService.updateFastestLap(currentSeason);

        currentSeasonJpaPort.saveAndFlush(currentSeason);
    }

    @Override
    public void updateCurrentSeasonForQualifying(CurrentSeason currentSeason, int position) {
        if(position == 1) currentSeasonDomainService.updatePolePosition(currentSeason);
        currentSeasonJpaPort.saveAndFlush(currentSeason);
    }

    @Override
    public void updateChampionshipRank(CurrentSeason currentSeason, int championshipRank) {
        currentSeasonDomainService.updateChampionshipRank(currentSeason, championshipRank);
        currentSeasonJpaPort.saveAndFlush(currentSeason);
    }
}
