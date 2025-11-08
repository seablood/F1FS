package kr.co.F1FS.app.domain.reply.application.port.out;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;

import java.util.List;

public interface ReplyJpaPort {
    Reply save(Reply reply);
    Reply saveAndFlush(Reply reply);
    Reply findById(Long id);
    List<ResponseReplyDTO> findAllByPost(Post post);
    void delete(Reply reply);
}
