package kr.co.F1FS.app.domain.reply.application.port.out.replying;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListByUserDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyJpaPort {
    Reply save(Reply reply);
    Reply saveAndFlush(Reply reply);
    Reply findByIdForPostWithJoin(Long id);
    List<ResponseReplyListDTO> findAllByPost(Long postId);
    Page<ResponseReplyListByUserDTO> findAllByUser(Long userId, Pageable pageable);
    void delete(Reply reply);
}
