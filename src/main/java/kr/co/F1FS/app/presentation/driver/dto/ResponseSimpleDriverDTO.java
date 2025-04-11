package kr.co.F1FS.app.presentation.driver.dto;

import kr.co.F1FS.app.domain.model.rdb.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ResponseSimpleDriverDTO {
    private String name;
    private Integer number;
    private String country;
    private String constructorName;

    public static ResponseSimpleDriverDTO toDto(Driver driver){
        return new ResponseSimpleDriverDTO(driver.getName(), driver.getNumber(), driver.getCountry(), driver.getTeam());
    }
}
