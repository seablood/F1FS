package kr.co.F1FS.app.domain.suggest.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseSuggestListDTO {
    private Long id;
    private String author;
    private String title;
    private String createdAt;
    private boolean isConfirmed;

    @QueryProjection
    public ResponseSuggestListDTO(Long id, String author, String title, Timestamp createdAt, boolean isConfirmed){
        this.id = id;
        this.author = author;
        this.title = title;
        this.createdAt = TimeUtil.formatPostTime(TimeUtil.convertToKoreanTime(createdAt));
        this.isConfirmed = isConfirmed;
    }
}
