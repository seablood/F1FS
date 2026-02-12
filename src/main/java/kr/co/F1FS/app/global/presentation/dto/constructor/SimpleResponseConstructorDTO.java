package kr.co.F1FS.app.global.presentation.dto.constructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponseConstructorDTO {
    private Long id;

    private String korName;

    private String engName;

    private String racingClass;
}
