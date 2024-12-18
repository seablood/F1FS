package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.Constructor;
import kr.co.F1FS.app.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
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

    public static ResponseConstructorDTO toDto(Constructor constructor, List<String> drivers){
        return new ResponseConstructorDTO(constructor.getName(), drivers, constructor.getChampionships(),
                constructor.getBase(), constructor.getTeamChief(), constructor.getChassis(), constructor.getPowerUnit());
    }
}
