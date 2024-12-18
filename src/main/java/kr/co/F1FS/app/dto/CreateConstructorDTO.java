package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.Constructor;
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

    public static Constructor toEntity(CreateConstructorDTO dto){
        return Constructor.builder()
                .name(dto.getName())
                .championships(dto.getChampionships())
                .base(dto.getBase())
                .teamChief(dto.getTeamChief())
                .chassis(dto.getChassis())
                .powerUnit(dto.getPowerUnit())
                .build();
    }
}
