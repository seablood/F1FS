package kr.co.F1FS.app.domain.record.presentation.dto.currentSeason;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCurrentSeasonDTO {
    private Integer championshipRank;
    private Integer championshipPoint;
    private Integer podiums;
    private Integer highestFinish;
    private Integer fastestLap;
    private Integer polePosition;

    public static CurrentSeason toEntity(CreateCurrentSeasonDTO dto){
        return CurrentSeason.builder()
                .championshipRank(dto.getChampionshipRank())
                .championshipPoint(dto.getChampionshipPoint())
                .podiums(dto.getPodiums())
                .highestFinish(dto.getHighestFinish())
                .fastestLap(dto.getFastestLap())
                .polePosition(dto.getPolePosition())
                .build();
    }
}
