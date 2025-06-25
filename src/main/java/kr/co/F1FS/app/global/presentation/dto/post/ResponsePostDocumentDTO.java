package kr.co.F1FS.app.global.presentation.dto.post;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDocumentDTO {
    private Long id;

    private String title;

    private String author;

    private String createdAt;

    private int likeNum;
}
