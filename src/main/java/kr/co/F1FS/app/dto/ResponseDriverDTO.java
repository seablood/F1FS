package kr.co.F1FS.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResponseDriverDTO {
    private String name;
    private String engName;
    private Integer number;
    private Integer championships;
    private String country;
    private Integer followerNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String constructorName;
    private ResponseCurrentSeasonDTO currentSeason;
    private ResponseSinceDebutDTO sinceDebut;

    public static ResponseDriverDTO toDto(Driver driver, ResponseCurrentSeasonDTO seasonDTO,
                                          ResponseSinceDebutDTO debutDTO){
        return new ResponseDriverDTO(driver.getName(), driver.getEngName(), driver.getNumber(), driver.getChampionships(),
                driver.getCountry(), driver.getFollowerNum(), driver.getBirth(), driver.getTeam(), seasonDTO, debutDTO);
    }
}
