package kr.co.F1FS.app.domain.record.application.mapper;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {
    public CurrentSeason toCurrentSeason(CreateCurrentSeasonDTO dto){
        return CurrentSeason.builder()
                .championshipRank(dto.getChampionshipRank())
                .championshipPoint(dto.getChampionshipPoint())
                .podiums(dto.getPodiums())
                .highestFinish(dto.getHighestFinish())
                .fastestLap(dto.getFastestLap())
                .polePosition(dto.getPolePosition())
                .build();
    }

    public SinceDebut toSinceDebut(CreateSinceDebutDTO dto){
        return SinceDebut.builder()
                .podiums(dto.getPodiums())
                .highestFinish(dto.getHighestFinish())
                .fastestLap(dto.getFastestLap())
                .polePosition(dto.getPolePosition())
                .enteredGP(dto.getEnteredGP())
                .build();
    }

    public ResponseCurrentSeasonDTO toResponseCurrentSeasonDTO(CurrentSeason currentSeason){
        return ResponseCurrentSeasonDTO.builder()
                .championshipRank(currentSeason.getChampionshipRank())
                .championshipPoint(currentSeason.getChampionshipPoint())
                .podiums(currentSeason.getPodiums())
                .highestFinish(currentSeason.getHighestFinish())
                .fastestLap(currentSeason.getFastestLap())
                .polePosition(currentSeason.getPolePosition())
                .build();
    }

    public ResponseSinceDebutDTO toResponseSinceDebutDTO(SinceDebut sinceDebut){
        return ResponseSinceDebutDTO.builder()
                .podiums(sinceDebut.getPodiums())
                .highestFinish(sinceDebut.getHighestFinish())
                .fastestLap(sinceDebut.getFastestLap())
                .polePosition(sinceDebut.getPolePosition())
                .enteredGP(sinceDebut.getEnteredGP())
                .build();
    }
}
