package kr.co.F1FS.app.domain.constructor.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyConstructorCommand {
    private String name;
    private String engName;
    private Integer championships;
    private String base;
    private String teamChief;
    private String chassis;
    private String powerUnit;
}
