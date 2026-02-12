package kr.co.F1FS.app.global.presentation.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDocumentDTO {
    private Long id;
    private String title;
    private String author;
    private List<String> tags;
    private String createdAt;
    private int likeNum;
}
