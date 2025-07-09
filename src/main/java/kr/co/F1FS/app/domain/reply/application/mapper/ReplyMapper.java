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
        String author = reply.getUser() == null ? "알 수 없음" : reply.getUser().getNickname();

        return ResponseReplyDTO.builder()
                .author(author)
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }
}
