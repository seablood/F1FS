package kr.co.F1FS.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.util.RacingClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDriverDTO {
    private String name;
    private String engName;
    private Integer number;
    private Integer championships;
    private String country;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;
    private String constructorName;

    public static Driver toEntity(CreateDriverDTO dto){
        return Driver.builder()
                .name(dto.getName())
                .engName(dto.getEngName())
                .number(dto.getNumber())
                .team(dto.getConstructorName())
                .championships(dto.getChampionships())
                .country(dto.getCountry())
                .birth(dto.getBirth())
                .racingClass(dto.getRacingClass())
                .build();
    }
}
