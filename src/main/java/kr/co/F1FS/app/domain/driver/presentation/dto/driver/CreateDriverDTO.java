package kr.co.F1FS.app.domain.driver.presentation.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDriverDTO {
    @NotBlank(message = "name은 필수 입력 항목입니다.")
    private String name;
    @NotBlank(message = "engName은 필수 입력 항목입니다.")
    private String engName;
    private Integer number;
    private Integer championships;
    @NotBlank(message = "country는 필수 입력 항목입니다.")
    private String country;
    private Integer followerNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp birth;
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;
    @NotBlank(message = "Driver의 Constructor 소속 여부를 확인해주세요.")
    private String constructorName;

    public static Driver toEntity(CreateDriverDTO dto){
        return Driver.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .number(dto.getNumber())
                .team(dto.getConstructorName())
                .championships(dto.getChampionships())
                .country(dto.getCountry())
                .followerNum(dto.getFollowerNum())
                .birth(dto.getBirth())
                .racingClass(dto.getRacingClass())
                .build();
    }
}
