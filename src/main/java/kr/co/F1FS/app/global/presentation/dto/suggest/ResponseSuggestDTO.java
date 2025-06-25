package kr.co.F1FS.app.global.presentation.dto.suggest;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSuggestDTO {
    private Long id;
    private String author;
    private String title;
    private String content;
    private String createdAt;
    private boolean isConfirmed;

    public static ResponseSuggestDTO toDto(Suggest suggest){
        LocalDateTime suggestTime = TimeUtil.convertToKoreanTime(suggest.getCreatedAt());

        return new ResponseSuggestDTO(suggest.getId(), suggest.getFromUser().getNickname(), suggest.getTitle(),
                suggest.getContent(), TimeUtil.formatPostTime(suggestTime), suggest.isConfirmed());
    }
}
