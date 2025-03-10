package kr.co.F1FS.app.dto;

import kr.co.F1FS.app.model.Reply;
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
