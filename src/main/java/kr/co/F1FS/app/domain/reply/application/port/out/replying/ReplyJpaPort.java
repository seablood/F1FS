package kr.co.F1FS.app.domain.reply.application.port.out.replying;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyJpaPort {
    Reply save(Reply reply);
    Reply saveAndFlush(Reply reply);
    Reply findById(Long id);
    List<Reply> findAllByPost(Post post);
    Page<Reply> findAllByUser(User user, Pageable pageable);
    void delete(Reply reply);
}
