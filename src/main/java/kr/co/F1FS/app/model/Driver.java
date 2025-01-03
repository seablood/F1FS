package kr.co.F1FS.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kr.co.F1FS.app.util.RacingClass;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String engName;
    private Integer number;
    private String team;
    private Integer championships = 0;
    private String country;
    @JsonIgnore
    @OneToMany(mappedBy = "driverInfo")
    private List<DriverRecordRelation> records = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "driverSinceInfo")
    private List<DriverDebutRelation> debuts = new ArrayList<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;
    //
    // 드라이버 이미지 제공 필요
                       //

    public void updateTeam(String team){
        this.team = team;
    }

    public void updateRacingClass(RacingClass racingClass){
        this.racingClass = racingClass;
    }

    @Builder
    public Driver(String name, String engName, Integer number, String team, Integer championships, String country, LocalDate birth,
                  RacingClass racingClass){
        this.name = name;
        this.engName = engName;
        this.number = number;
        this.team = team;
        this.championships = championships;
        this.country = country;
        this.birth = birth;
        this.racingClass = racingClass;
    }
}
