package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@NoArgsConstructor ** 임시 조치 **
@AllArgsConstructor
@Table(name = "since_debut")
public class SinceDebut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer podiums;
    private Integer highestFinish;
    private Integer fastestLap;
    private Integer polePosition;
    private Integer enteredGP;

    @Builder
    public SinceDebut(Integer podiums, Integer highestFinish, Integer fastestLap, Integer polePosition,
                      Integer enteredGP){
        this.podiums = podiums;
        this.highestFinish = highestFinish;
        this.fastestLap = fastestLap;
        this.polePosition = polePosition;
        this.enteredGP = enteredGP;
    }

    public SinceDebut() {
        this.podiums = 0;
        this.highestFinish = 0;
        this.fastestLap = 0;
        this.polePosition = 0;
        this.enteredGP = 0;
    }
}
