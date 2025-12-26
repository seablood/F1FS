package kr.co.F1FS.app.domain.reply.application.mapper;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.CreateReplyCommentDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.CreateReplyDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyByUserDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
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

    public ReplyComment toReplyComment(CreateReplyCommentDTO dto, User user, Reply reply){
        return ReplyComment.builder()
                .content(dto.getContent())
                .user(user)
                .reply(reply)
                .build();
    }

    public ReplyLikeRelation toReplyLikeRelation(User user, Reply reply){
        return ReplyLikeRelation.builder()
                .user(user)
                .reply(reply)
                .build();
    }

    public ResponseReplyDTO toResponseReplyDTO(Reply reply){
        String author = reply.getUser() == null ? "알 수 없음" : reply.getUser().getNickname();

        return ResponseReplyDTO.builder()
                .id(reply.getId())
                .author(author)
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }

    public ResponseReplyByUserDTO toResponseReplyByUserDTO(Reply reply){
        return ResponseReplyByUserDTO.builder()
                .postId(reply.getPost().getId())
                .author(reply.getUser().getNickname())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }

    public ResponseReplyCommentDTO toResponseReplyCommentDTO(ReplyComment replyComment){
        String author = replyComment.getUser() == null ? "알 수 없음" : replyComment.getUser().getNickname();

        return ResponseReplyCommentDTO.builder()
                .id(replyComment.getId())
                .author(author)
                .content(replyComment.getContent())
                .createdAt(replyComment.getCreatedAt())
                .updatedAt(replyComment.getUpdatedAt())
                .build();
    }
}
