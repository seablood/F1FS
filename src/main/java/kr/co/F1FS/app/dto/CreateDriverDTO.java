package kr.co.F1FS.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDriverDTO {
    private String name;
    private Integer number;
    private Integer championships;
    private String country;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String constructorName;

    public static Driver toEntity(CreateDriverDTO dto){
        return Driver.builder()
                .name(dto.getName())
                .number(dto.getNumber())
                .team(dto.getConstructorName())
                .championships(dto.getChampionships())
                .country(dto.getCountry())
                .birth(dto.getBirth())
                .build();
    }
}
