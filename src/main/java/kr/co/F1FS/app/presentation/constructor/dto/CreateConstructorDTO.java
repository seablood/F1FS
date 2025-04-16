package kr.co.F1FS.app.presentation.constructor.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.F1FS.app.domain.model.rdb.Constructor;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConstructorDTO {
    @NotBlank(message = "name은 필수 입력 항목입니다.")
    private String name;
    @NotBlank(message = "engName은 필수 입력 항목입니다.")
    private String engName;
    private Integer championships;
    @NotBlank(message = "base는 필수 입력 항목입니다.")
    private String base;
    @NotNull(message = "teamChief의 여부를 확인해주세요.")
    private String teamChief;
    @NotNull(message = "해당 Constructor의 차량 개발 현황을 확인해주세요.")
    private String chassis;
    @NotNull(message = "해당 Constructor 차량의 PowerUnit을 확인해주세요.")
    private String powerUnit;
    private Integer followerNum;
    @Enumerated(value = EnumType.STRING)
    @NotBlank(message = "RacingClass는 필수 입력 항목입니다.")
    private RacingClass racingClass;

    public static Constructor toEntity(CreateConstructorDTO dto){
        return Constructor.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .championships(dto.getChampionships())
                .base(dto.getBase())
                .teamChief(dto.getTeamChief())
                .chassis(dto.getChassis())
                .powerUnit(dto.getPowerUnit())
                .followerNum(dto.getFollowerNum())
                .racingClass(dto.getRacingClass())
                .build();
    }
}
