package kr.co.F1FS.app.presentation.search.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CDSearchSuggestionDTO {
    private Long id;

    private String korName;

    private String engName;

    private String racingClass;

    private String type;
}
