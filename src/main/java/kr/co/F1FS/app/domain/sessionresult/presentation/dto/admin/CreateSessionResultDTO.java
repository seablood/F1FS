package kr.co.F1FS.app.domain.sessionresult.presentation.dto.admin;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.F1FS.app.global.util.RaceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSessionResultDTO {
    @NotBlank(message = "driverName은 필수 입력 항목입니다.")
    private String driverName;
    @NotBlank(message = "constructorName은 필수 입력 항목입니다.")
    private String constructorName;
    @NotNull(message = "position은 필수 입력 항목입니다.")
    private Integer position;
    @NotBlank(message = "timeOrGap은 필수 입력 항목입니다.")
    private String timeOrGap;
    @NotNull(message = "points는 필수 입력 항목입니다.")
    private Integer points;
    @NotNull(message = "isFastestLap는 필수 입력 항목입니다.")
    private Integer isFastestLap;
    @Enumerated(value = EnumType.STRING)
    private RaceStatus raceStatus;

    public boolean fastestLap(){
        if(this.isFastestLap.equals(1)) return true;
        else return false;
    }
}
