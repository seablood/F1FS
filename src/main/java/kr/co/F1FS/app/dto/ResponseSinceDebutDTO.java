package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.SinceDebut;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
