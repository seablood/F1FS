package kr.co.F1FS.app.presentation.constructor.dto;

import kr.co.F1FS.app.domain.model.rdb.Constructor;
import kr.co.F1FS.app.presentation.record.dto.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.presentation.record.dto.ResponseSinceDebutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseConstructorDTO {
    private String name;
    private String engName;
    private List<String> drivers;
    private Integer championships;
    private String base;
    private String teamChief;
    private String chassis;
    private String powerUnit;
    private Integer followerNum;
    private ResponseCurrentSeasonDTO currentSeason;
    private ResponseSinceDebutDTO sinceDebut;

    public static ResponseConstructorDTO toDto(Constructor constructor, List<String> drivers,
                                               ResponseCurrentSeasonDTO currentSeason,
                                               ResponseSinceDebutDTO sinceDebut){
        return new ResponseConstructorDTO(constructor.getName(), constructor.getEngName(), drivers,
                constructor.getChampionships(), constructor.getBase(), constructor.getTeamChief(),
                constructor.getChassis(), constructor.getPowerUnit(), constructor.getFollowerNum(),
                currentSeason, sinceDebut);
    }
}
