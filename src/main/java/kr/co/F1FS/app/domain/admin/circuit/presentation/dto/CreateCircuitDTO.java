package kr.co.F1FS.app.domain.admin.circuit.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCircuitDTO {
    @NotBlank(message = "name은 필수 입력 항목입니다.")
    private String name;
    @NotBlank(message = "engName은 필수 입력 항목입니다.")
    private String engName;
    @NotNull(message = "length는 null이 될 수 없습니다.")
    private Double length;
    @NotNull(message = "first_grand_prix는 null이 될 수 없습니다.")
    private Integer first_grand_prix;
    @NotNull(message = "laps는 null이 될 수 없습니다.")
    private Integer laps;
    @NotNull(message = "fastestLap는 null이 될 수 없습니다.")
    private String fastestLap;
    @NotNull(message = "raceDistance는 null이 될 수 없습니다.")
    private Double raceDistance;
}
