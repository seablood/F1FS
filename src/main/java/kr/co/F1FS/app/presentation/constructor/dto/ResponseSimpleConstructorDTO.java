package kr.co.F1FS.app.presentation.constructor.dto;

import kr.co.F1FS.app.domain.model.rdb.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseSimpleConstructorDTO {
    private String name;
    private String base;
    private String teamChief;

    public static ResponseSimpleConstructorDTO toDto(Constructor constructor){
        return new ResponseSimpleConstructorDTO(constructor.getName(), constructor.getBase(), constructor.getTeamChief());
    }
}
