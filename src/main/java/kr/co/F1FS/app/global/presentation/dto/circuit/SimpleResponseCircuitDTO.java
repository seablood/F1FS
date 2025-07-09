package kr.co.F1FS.app.global.presentation.dto.circuit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseCircuitDTO {
    private Long id;
    private String name;
    private String engName;
}
