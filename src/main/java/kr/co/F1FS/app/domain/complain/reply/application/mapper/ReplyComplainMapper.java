package kr.co.F1FS.app.domain.complain.reply.application.mapper;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.CreateReplyComplainDTO;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import org.springframework.stereotype.Component;

@Component
public class ReplyComplainMapper {
    public ReplyComplain toReplyComplain(Reply reply, User user, CreateReplyComplainDTO dto){
        return ReplyComplain.builder()
                .toReply(reply)
                .fromUser(user)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();
    }

    public ResponseReplyComplainDTO toResponseReplyComplainDTO(ReplyComplain replyComplain){
        return ResponseReplyComplainDTO.builder()
                .id(replyComplain.getId())
                .replyId(replyComplain.getToReply().getId())
                .replyContent(replyComplain.getToReply().getContent())
                .fromUserNickname(replyComplain.getFromUser().getNickname())
                .description(replyComplain.getDescription())
                .paraphrase(replyComplain.getParaphrase())
                .build();
    }
}
