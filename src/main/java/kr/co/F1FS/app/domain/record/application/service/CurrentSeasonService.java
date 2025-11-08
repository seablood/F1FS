package kr.co.F1FS.app.domain.record.application.service;

import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.domain.record.application.port.in.CurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.out.CurrentSeasonJpaPort;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentSeasonService implements CurrentSeasonUseCase {
    private final CurrentSeasonJpaPort currentSeasonJpaPort;
    private final RecordMapper recordMapper;

    @Override
    public CurrentSeason save(CurrentSeason currentSeason) {
        return currentSeasonJpaPort.save(currentSeason);
    }

    @Override
    public CurrentSeason saveAndFlush(CurrentSeason currentSeason) {
        return currentSeasonJpaPort.saveAndFlush(currentSeason);
    }

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

        currentSeasonJpaPort.saveAndFlush(currentSeason);
    }

    @Override
    public void updateCurrentSeasonForQualifying(CurrentSeason currentSeason, int position) {
        if(position == 1) currentSeason.updatePolePosition();
        currentSeasonJpaPort.saveAndFlush(currentSeason);
    }

    @Override
    public void updateChampionshipRank(CurrentSeason currentSeason, int championshipRank){
        currentSeason.updateChampionshipRank(championshipRank);
        currentSeasonJpaPort.saveAndFlush(currentSeason);
    }

    @Override
    public CurrentSeason toCurrentSeason(CreateCurrentSeasonDTO dto) {
        return recordMapper.toCurrentSeason(dto);
    }

    @Override
    public ResponseCurrentSeasonDTO toResponseCurrentSeasonDTO(CurrentSeason currentSeason) {
        return recordMapper.toResponseCurrentSeasonDTO(currentSeason);
    }
}
