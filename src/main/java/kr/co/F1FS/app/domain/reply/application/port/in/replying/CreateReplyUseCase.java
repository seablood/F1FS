package kr.co.F1FS.app.domain.reply.application.port.in.replying;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.CreateReplyDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateReplyUseCase {
    Reply save(CreateReplyDTO dto, User user, Post post);
}
