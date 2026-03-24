package kr.co.F1FS.app.domain.reply.infrastructure.adapter.replying;

import kr.co.F1FS.app.domain.reply.application.port.out.replying.ReplyJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.ReplyRepository;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl.ReplyDSLRepository;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListByUserDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListDTO;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyJpaAdapter implements ReplyJpaPort {
    private final ReplyRepository replyRepository;
    private final ReplyDSLRepository replyDSLRepository;

    @Override
    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }

    @Override
    public Reply saveAndFlush(Reply reply) {
        return replyRepository.saveAndFlush(reply);
    }

    @Override
    public Reply findByIdForPostWithJoin(Long id) {
        return replyDSLRepository.findByIdForPost(id)
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND));
    }

    @Override
    public List<ResponseReplyListDTO> findAllByPost(Long postId) {
        return replyDSLRepository.findAllByPost(postId);
    }

    @Override
    public Page<ResponseReplyListByUserDTO> findAllByUser(Long userId, Pageable pageable) {
        return replyDSLRepository.findAllByUser(userId, pageable);
    }

    @Override
    public void delete(Reply reply) {
        replyRepository.delete(reply);
    }
}
