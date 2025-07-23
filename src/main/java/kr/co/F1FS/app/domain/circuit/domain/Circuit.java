package kr.co.F1FS.app.domain.circuit.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import kr.co.F1FS.app.domain.circuit.presentation.dto.ModifyCircuitCommand;
import kr.co.F1FS.app.global.util.LapMillisUtil;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "circuits")
public class Circuit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String engName;
    @Positive(message = "length값을 확인해주세요.")
    private Double length;
    @Positive(message = "first_grand_prix값을 확인해주세요.")
    private Integer first_grand_prix;
    @Positive(message = "laps값을 확인해주세요.")
    private Integer laps;
    @Positive(message = "fastestLap값을 확인해주세요.")
    private Long fastestLap;
    private String fastestLapDriver;
    @Positive(message = "fastestLapSeason값을 확인해주세요.")
    private Integer fastestLapSeason;
    @Positive(message = "raceDistance값을 확인해주세요.")
    private Double raceDistance;

    public void modify(ModifyCircuitCommand command){
        this.name = command.getName();
        this.engName = command.getEngName();
        this.length = command.getLength();
        this.first_grand_prix = command.getFirst_grand_prix();
        this.laps = command.getLaps();
        this.fastestLap = LapMillisUtil.fastestToMillis(command.getFastestLap());
        this.fastestLapDriver = command.getFastestLapDriver();
        this.fastestLapSeason = command.getFastestLapSeason();
        this.raceDistance = command.getRaceDistance();
    }

    @Builder
    public Circuit(String name, String engName, Double length, Integer first_grand_prix, Integer laps, Long fastestLap,
                   String fastestLapDriver, Integer fastestLapSeason, Double raceDistance){
        this.name = name;
        this.engName = engName;
        this.length = length;
        this.first_grand_prix = first_grand_prix;
        this.laps = laps;
        this.fastestLap = fastestLap;
        this.fastestLapDriver = fastestLapDriver;
        this.fastestLapSeason = fastestLapSeason;
        this.raceDistance = raceDistance;
    }
}
