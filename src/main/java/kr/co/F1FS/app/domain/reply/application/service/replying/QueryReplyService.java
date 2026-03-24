package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.reply.application.port.in.replying.QueryReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replying.ReplyJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListByUserDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryReplyService implements QueryReplyUseCase {
    private final ReplyJpaPort replyJpaPort;

    @Override
    public Reply findByIdForPostWithJoin(Long id) {
        return replyJpaPort.findByIdForPostWithJoin(id);
    }

    @Override
    public List<ResponseReplyListDTO> findAllByPostForDTO(Long postId) {
        return replyJpaPort.findAllByPost(postId);
    }

    @Override
    public Page<ResponseReplyListByUserDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return replyJpaPort.findAllByUser(userId, pageable);
    }
}
