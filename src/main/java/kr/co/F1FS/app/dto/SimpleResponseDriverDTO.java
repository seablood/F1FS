package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.Driver;
import kr.co.F1FS.app.util.RacingClass;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleResponseDriverDTO {
    private String name;
    private String engName;
    private Integer number;
    private String team;
    private RacingClass racingClass;

    public static SimpleResponseDriverDTO toDto(Driver driver){
        return new SimpleResponseDriverDTO(driver.getName(), driver.getEngName(), driver.getNumber(), driver.getTeam(),
                driver.getRacingClass());
    }
}
