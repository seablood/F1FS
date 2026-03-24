package kr.co.F1FS.app.domain.complain.post.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponsePostComplainListDTO {
    private Long id;
    private Long postId;
    private String description;

    @QueryProjection
    public ResponsePostComplainListDTO(Long id, Long postId, String description){
        this.id = id;
        this.postId = postId;
        this.description = description;
    }
}
