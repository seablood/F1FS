package kr.co.F1FS.app.global.presentation.dto.record;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
