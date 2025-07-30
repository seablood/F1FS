package kr.co.F1FS.app.domain.driver.domain.rdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import kr.co.F1FS.app.domain.driver.presentation.dto.ModifyDriverCommand;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.*;

import java.sql.Timestamp;

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
    @Positive(message = "Driver Number를 확인해주세요.")
    @Column(unique = true)
    private Integer number;
    private String team;
    private String engTeam;
    private Integer championships;
    private String country;
    private Integer followerNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp birth;
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;
    //
    // 드라이버 이미지 제공 필요
                       //

    public void modify(ModifyDriverCommand command){
        this.name = command.getName();
        this.engName = command.getEngName();
        this.number = command.getNumber();
        this.championships = command.getChampionships();
        this.country = command.getCountry();
        this.birth = command.getBirth();
    }

    public void updateTeam(String team, String engTeam){
        this.team = team;
        this.engTeam = engTeam;
    }

    public void updateRacingClass(RacingClass racingClass){
        this.racingClass = racingClass;
    }

    public void updateEngTeam(String engTeam){
        this.engTeam = engTeam;
    }

    public void increaseFollower(){
        this.followerNum++;
    }

    public void decreaseFollower(){
        this.followerNum--;
    }

    @Builder
    public Driver(String name, String engName, Integer number, String team, Integer championships, String country, Timestamp birth,
                  RacingClass racingClass, Integer followerNum){
        this.name = name;
        this.engName = engName;
        this.number = number;
        this.team = team;
        this.championships = championships;
        this.country = country;
        this.followerNum = followerNum;
        this.birth = birth;
        this.racingClass = racingClass;
    }
}
