package kr.co.F1FS.app.presentation.reply.dto;

import kr.co.F1FS.app.domain.model.rdb.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseReplyDTO {
    private String author;
    private String content;

    public static ResponseReplyDTO toDto(Reply reply){
        return new ResponseReplyDTO(reply.getUser().getNickname(), reply.getContent());
    }
}
