package kr.co.F1FS.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDriverDTO {
    private String name;
    private Integer number;
    private Integer championships;
    private String country;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String constructorName;
    private ResponseCurrentSeasonDTO currentSeasonDTO;

    public static ResponseDriverDTO toDto(Driver driver){
        return new ResponseDriverDTO(driver.getName(), driver.getNumber(), driver.getChampionships(),
                driver.getCountry(), driver.getBirth(), driver.getTeam(),
                ResponseCurrentSeasonDTO.toDto(driver.getRecords().stream().filter(recordRelation ->
                                recordRelation.getRacingClass() == driver.getRacingClass()).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("드라이버 정보 오류")).getCurrentSeason()));
    }
}
