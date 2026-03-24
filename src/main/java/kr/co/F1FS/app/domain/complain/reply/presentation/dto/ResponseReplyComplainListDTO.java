package kr.co.F1FS.app.domain.complain.reply.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseReplyComplainListDTO {
    private Long id;
    private String replyContent;
    private String description;

    @QueryProjection
    public ResponseReplyComplainListDTO(Long id, String replyContent, String description){
        this.id = id;
        this.replyContent = replyContent;
        this.description = description;
    }
}
