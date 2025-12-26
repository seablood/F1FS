package kr.co.F1FS.app.domain.record.application.service.currentSeason;

import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentSeasonDomainService {
    private final RecordMapper recordMapper;

    public CurrentSeason createEntity(CreateCurrentSeasonDTO currentSeasonDTO){
        return recordMapper.toCurrentSeason(currentSeasonDTO);
    }

    public CurrentSeason createEntity(){
        return new CurrentSeason();
    }

    public void updateChampionshipPoint(CurrentSeason currentSeason, int points){
        currentSeason.updateChampionshipPoint(points);
    }

    public void updatePodiums(CurrentSeason currentSeason){
        currentSeason.updatePodiums();
    }

    public void updateHighestFinish(CurrentSeason currentSeason){
        currentSeason.updateHighestFinish();
    }

    public void updateFastestLap(CurrentSeason currentSeason){
        currentSeason.updateFastestLap();
    }

    public void updatePolePosition(CurrentSeason currentSeason){
        currentSeason.updatePolePosition();
    }

    public void updateChampionshipRank(CurrentSeason currentSeason, int championshipRank){
        currentSeason.updateChampionshipRank(championshipRank);
    }
}
