package kr.co.F1FS.app.presentation.post.dto;

import kr.co.F1FS.app.domain.model.elastic.PostDocument;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDocumentDTO {
    private Long id;

    private String title;

    private String author;

    private String createdAt;

    private int likeNum;

    public static ResponsePostDocumentDTO toDto(PostDocument postDocument){
        return new ResponsePostDocumentDTO(postDocument.getId(), postDocument.getTitle(), postDocument.getAuthor(),
                TimeUtil.formatPostTime(postDocument.getCreatedAt()), postDocument.getLikeNum());
    }
}
