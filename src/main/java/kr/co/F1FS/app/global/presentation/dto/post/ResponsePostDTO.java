package kr.co.F1FS.app.global.presentation.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.domain.post.domain.PostBlock;
import kr.co.F1FS.app.global.util.PostBlockType;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDTO {
    private Long id;
    private String title;
    private List<BlockContent> blocks;
    private String author;
    private List<String> tags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp updatedAt;
    private int likeNum;

    @Getter
    @NoArgsConstructor
    public static class BlockContent {
        private String content;
        private PostBlockType type;

        public BlockContent(String content, PostBlockType blockType) {
            this.content = content;
            this.type = blockType;
        }
    }
}
