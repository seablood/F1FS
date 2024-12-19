package kr.co.F1FS.app.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.util.RacingClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConstructorDTO {
    private String name;
    private Integer championships;
    private String base;
    private String teamChief;
    private String chassis;
    private String powerUnit;
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;

    public static Constructor toEntity(CreateConstructorDTO dto){
        return Constructor.builder()
                .name(dto.getName())
                .championships(dto.getChampionships())
                .base(dto.getBase())
                .teamChief(dto.getTeamChief())
                .chassis(dto.getChassis())
                .powerUnit(dto.getPowerUnit())
                .racingClass(dto.getRacingClass())
                .build();
    }
}
