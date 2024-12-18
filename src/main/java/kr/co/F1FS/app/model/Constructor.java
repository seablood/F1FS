package kr.co.F1FS.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    @OneToMany(mappedBy = "constructor")
    private List<ConstructorDriverRelation> drivers = new ArrayList<>();
    private Integer championships = 0;
    private String base;
    private String teamChief;
    private String chassis;
    private String powerUnit;
    //
    // 컨스트럭터 아이콘 이미지 제공 필요
    // 컨스트럭터 F1카 이미지 제공 필요
                             //

    @Builder
    public Constructor(String name, Integer championships, String base, String teamChief, String chassis, String powerUnit){
        this.name = name;
        this.championships = championships;
        this.base = base;
        this.teamChief = teamChief;
        this.chassis = chassis;
        this.powerUnit = powerUnit;
    }
}
