package kr.co.F1FS.app.presentation.record.dto;

import kr.co.F1FS.app.domain.model.rdb.CurrentSeason;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseCurrentSeasonDTO {
    private Integer championshipRank;
    private Integer championshipPoint;
    private Integer podiums;
    private Integer highestFinish;
    private Integer fastestLap;
    private Integer polePosition;

    public static ResponseCurrentSeasonDTO toDto(CurrentSeason currentSeason){
        return new ResponseCurrentSeasonDTO(currentSeason.getChampionshipRank(), currentSeason.getChampionshipPoint(),
                currentSeason.getPodiums(), currentSeason.getHighestFinish(), currentSeason.getFastestLap(),
                currentSeason.getPolePosition());
    }
}
