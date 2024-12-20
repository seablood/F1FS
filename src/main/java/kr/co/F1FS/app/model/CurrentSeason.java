package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import kr.co.F1FS.app.util.RacingClass;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "current_season")
public class CurrentSeason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer championshipRank = 0;
    private Integer championshipPoint = 0;
    private Integer podiums = 0;
    private Integer highestFinish = 0;
    private Integer fastestLap = 0;
    private Integer polePosition = 0;

    @Builder
    public CurrentSeason(Integer championshipRank, Integer championshipPoint, Integer podiums, Integer highestFinish,
                         Integer fastestLap, Integer polePosition){
        this.championshipRank = championshipRank;
        this.championshipPoint = championshipPoint;
        this.podiums = podiums;
        this.highestFinish = highestFinish;
        this.fastestLap = fastestLap;
        this.polePosition = polePosition;
    }
}
