package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseConstructorDTO {
    private String name;
    private List<String> drivers;
    private Integer championships;
    private String base;
    private String teamChief;
    private String chassis;
    private String powerUnit;
    private ResponseCurrentSeasonDTO currentSeason;
    private ResponseSinceDebutDTO sinceDebut;

    public static ResponseConstructorDTO toDto(Constructor constructor, List<String> drivers,
                                               ResponseCurrentSeasonDTO currentSeason,
                                               ResponseSinceDebutDTO sinceDebut){
        return new ResponseConstructorDTO(constructor.getName(), drivers, constructor.getChampionships(),
                constructor.getBase(), constructor.getTeamChief(), constructor.getChassis(),
                constructor.getPowerUnit(), currentSeason, sinceDebut);
    }
}
