package kr.co.F1FS.app.global.presentation.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDriverDTO {
    private String name;
    private String engName;
    private Integer number;
    private Integer championships;
    private String country;
    private Integer followerNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp birth;
    private String constructorName;
    private ResponseCurrentSeasonDTO currentSeason;
    private ResponseSinceDebutDTO sinceDebut;
}
