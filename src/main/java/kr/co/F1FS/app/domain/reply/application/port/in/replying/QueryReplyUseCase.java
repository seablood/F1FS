package kr.co.F1FS.app.domain.reply.application.port.in.replying;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyByUserDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface QueryReplyUseCase {
    Reply findById(Long id);
    List<Reply> findAllByPost(Post post);
    List<ResponseReplyDTO> findReplyList(Post post, List<Reply> replies, Map<Long, List<ResponseReplyCommentDTO>> commentMap);
    Page<ResponseReplyByUserDTO> findAllByUser(User user, Pageable pageable);
}
