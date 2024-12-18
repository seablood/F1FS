package kr.co.F1FS.app.model;

import jakarta.persistence.*;
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
    private Integer championshipRank;
    private Integer championshipPoint;
    private Integer podiums;
    private Integer highestFinish;
    private Integer fastestLap;
    private Integer polePosition;

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
