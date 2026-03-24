package kr.co.F1FS.app.domain.complain.note.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ResponseNoteComplainListDTO {
    private Long id;
    private Long noteId;
    private String description;

    @QueryProjection
    public ResponseNoteComplainListDTO(Long id, Long noteId, String description){
        this.id = id;
        this.noteId = noteId;
        this.description = description;
    }
}
