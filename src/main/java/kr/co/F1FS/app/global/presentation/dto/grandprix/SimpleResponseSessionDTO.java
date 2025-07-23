package kr.co.F1FS.app.global.presentation.dto.grandprix;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseSessionDTO {
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private SessionType sessionType;
    private String time;
}
