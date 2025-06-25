package kr.co.F1FS.app.domain.reply.application.mapper;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.CreateReplyDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ReplyMapper {
    public Reply toReply(CreateReplyDTO dto, User user, Post post){
        return Reply.builder()
                .content(dto.getContent())
                .user(user)
                .post(post)
                .build();
    }

    public ResponseReplyDTO toResponseReplyDTO(Reply reply){
        return ResponseReplyDTO.builder()
                .author(reply.getUser().getNickname())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }
}
