package kr.co.F1FS.app.global.presentation.dto.driver;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;
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
