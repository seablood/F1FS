package kr.co.F1FS.app.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.model.CurrentSeason;
import kr.co.F1FS.app.util.RacingClass;
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
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;

    public static ResponseCurrentSeasonDTO toDto(CurrentSeason currentSeason){
        return new ResponseCurrentSeasonDTO(currentSeason.getChampionshipRank(), currentSeason.getChampionshipPoint(),
                currentSeason.getPodiums(), currentSeason.getHighestFinish(), currentSeason.getFastestLap(),
                currentSeason.getPolePosition(), currentSeason.getRacingClass());
    }
}
