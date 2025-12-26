package kr.co.F1FS.app.domain.driver.presentation.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDriverDTO {
    private Long id;
    private String name;
    private String engName;
    private Integer number;
    private String team;
    private String engTeam;
    private Integer championships;
    private String country;
    private Integer followerNum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp birth;
    private String racingClass;
}
