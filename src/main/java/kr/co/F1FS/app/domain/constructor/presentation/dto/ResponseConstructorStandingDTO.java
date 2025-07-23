package kr.co.F1FS.app.domain.constructor.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseConstructorStandingDTO {
    private String constructorName;
    private Integer points;
}
