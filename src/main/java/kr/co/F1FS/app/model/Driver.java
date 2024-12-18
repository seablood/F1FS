package kr.co.F1FS.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private Integer number;
    private String team;
    private Integer championships = 0;
    private String country;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    //
    // 드라이버 이미지 제공 필요
                       //

    public void updateTeam(String team){
        this.team = team;
    }

    @Builder
    public Driver(String name, Integer number, String team, Integer championships, String country, LocalDate birth){
        this.name = name;
        this.number = number;
        this.team = team;
        this.championships = championships;
        this.country = country;
        this.birth = birth;
    }
}
