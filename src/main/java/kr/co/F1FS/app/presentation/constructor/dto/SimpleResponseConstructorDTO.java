package kr.co.F1FS.app.presentation.constructor.dto;

import kr.co.F1FS.app.domain.model.rdb.Constructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleResponseConstructorDTO {
    private Long id;

    private String korName;

    private String engName;

    private String racingClass;

    public static SimpleResponseConstructorDTO toDto(Constructor constructor){
        return new SimpleResponseConstructorDTO(constructor.getId(), constructor.getName(), constructor.getEngName(),
                constructor.getRacingClass().toString());
    }
}
