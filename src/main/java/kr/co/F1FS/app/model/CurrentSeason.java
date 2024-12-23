package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@NoArgsConstructor ** 임시 조치 **
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

    public CurrentSeason() {
        this.championshipRank = 0;
        this.championshipPoint = 0;
        this.podiums = 0;
        this.highestFinish = 0;
        this.fastestLap = 0;
        this.polePosition = 0;
    }

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
