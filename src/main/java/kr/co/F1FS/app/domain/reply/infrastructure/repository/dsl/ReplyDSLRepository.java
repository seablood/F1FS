package kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListByUserDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReplyDSLRepository {
    Optional<Reply> findByIdForPost(Long id);
    List<ResponseReplyListDTO> findAllByPost(Long postId);
    Page<ResponseReplyListByUserDTO> findAllByUser(Long userId, Pageable pageable);
}
