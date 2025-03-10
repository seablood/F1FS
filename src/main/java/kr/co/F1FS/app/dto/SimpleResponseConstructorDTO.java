package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.Constructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleResponseConstructorDTO {
    private String name;
    private String engName;

    public static SimpleResponseConstructorDTO toDto(Constructor constructor){
        return new SimpleResponseConstructorDTO(constructor.getName(), constructor.getEngName());
    }
}
