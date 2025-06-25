package kr.co.F1FS.app.domain.admin.constructor.presentation.dto;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseConstructorDTO {
    private Long id;
    private String name;
    private String engName;
    private Integer championships;
    private String base;
    private String teamChief;
    private String chassis;
    private String powerUnit;
    private Integer followerNum;
    private String racingClass;
}
