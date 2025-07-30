package kr.co.F1FS.app.domain.constructor.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.constructor.presentation.dto.ModifyConstructorCommand;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "constructors")
public class Constructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String engName;
    private Integer championships;
    private String base;
    private String teamChief;
    private String chassis;
    private String powerUnit;
    private Integer followerNum;
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;
    //
    // 컨스트럭터 아이콘 이미지 제공 필요
    // 컨스트럭터 F1카 이미지 제공 필요
                             //

    public void modify(ModifyConstructorCommand command){
        this.name = command.getName();
        this.engName = command.getEngName();
        this.championships = command.getChampionships();
        this.base = command.getBase();
        this.teamChief = command.getTeamChief();
        this.chassis = command.getChassis();
        this.powerUnit = command.getPowerUnit();
    }

    public void increaseFollower(){
        this.followerNum++;
    }

    public void decreaseFollower(){
        this.followerNum--;
    }

    @Builder
    public Constructor(String name, String engName, Integer championships, String base, String teamChief, String chassis,
                       String powerUnit, Integer followerNum, RacingClass racingClass){
        this.name = name;
        this.engName = engName;
        this.championships = championships;
        this.base = base;
        this.teamChief = teamChief;
        this.chassis = chassis;
        this.powerUnit = powerUnit;
        this.followerNum = followerNum;
        this.racingClass = racingClass;
    }
}
