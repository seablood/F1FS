package kr.co.F1FS.app.domain.reply.application.port.in.replying;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListByUserDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryReplyUseCase {
    Reply findByIdForPostWithJoin(Long id);
    List<ResponseReplyListDTO> findAllByPostForDTO(Long postId);
    Page<ResponseReplyListByUserDTO> findAllByUserForDTO(Long userId, Pageable pageable);
}
