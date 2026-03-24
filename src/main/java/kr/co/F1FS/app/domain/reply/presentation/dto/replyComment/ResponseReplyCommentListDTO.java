package kr.co.F1FS.app.domain.reply.presentation.dto.replyComment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseReplyCommentListDTO {
    private Long id;
    private String author;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp updatedAt;

    @QueryProjection
    public ResponseReplyCommentListDTO(Long id, String author, String content, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
