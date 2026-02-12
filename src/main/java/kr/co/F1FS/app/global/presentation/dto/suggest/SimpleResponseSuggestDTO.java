package kr.co.F1FS.app.global.presentation.dto.suggest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseSuggestDTO {
    private Long id;
    private String author;
    private String title;
    private String createdAt;
    private boolean isConfirmed;
}
