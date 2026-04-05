package kr.co.F1FS.app.global.presentation.dto.bookmark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBookmarkDocumentDTO {
    private Long postId;
    private String title;
    private String author;
    private String markingTime;
}
