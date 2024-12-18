package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.SinceDebut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSinceDebutDTO {
    private Integer podiums;
    private Integer highestFinish;
    private Integer fastestLap;
    private Integer polePosition;
    private Integer enteredGP;

    public static SinceDebut toEntity(CreateSinceDebutDTO dto){
        return SinceDebut.builder()
                .podiums(dto.getPodiums())
                .highestFinish(dto.getHighestFinish())
                .fastestLap(dto.getFastestLap())
                .polePosition(dto.getPolePosition())
                .enteredGP(dto.getEnteredGP())
                .build();
    }
}
