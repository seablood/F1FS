package kr.co.F1FS.app.global.presentation.dto.record;

import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSinceDebutDTO {
    private Integer podiums;
    private Integer highestFinish;
    private Integer fastestLap;
    private Integer polePosition;
    private Integer enteredGP;

    public static ResponseSinceDebutDTO toDto(SinceDebut sinceDebut){
        return new ResponseSinceDebutDTO(sinceDebut.getPodiums(), sinceDebut.getHighestFinish(),
                sinceDebut.getFastestLap(), sinceDebut.getPolePosition(), sinceDebut.getEnteredGP());
    }
}
