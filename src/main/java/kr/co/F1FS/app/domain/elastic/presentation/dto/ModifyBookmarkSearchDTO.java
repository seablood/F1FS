package kr.co.F1FS.app.domain.elastic.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyBookmarkSearchDTO {
    private Long postId;
    private String title;
}
