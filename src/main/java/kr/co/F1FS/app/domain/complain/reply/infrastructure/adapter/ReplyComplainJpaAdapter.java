package kr.co.F1FS.app.domain.complain.reply.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.reply.application.port.out.ReplyComplainJpaPort;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.infrastructure.repository.ReplyComplainRepository;
import kr.co.F1FS.app.domain.complain.reply.infrastructure.repository.dsl.ReplyComplainDSLRepository;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyComplainJpaAdapter implements ReplyComplainJpaPort {
    private final ReplyComplainRepository replyComplainRepository;
    private final ReplyComplainDSLRepository replyComplainDSLRepository;

    @Override
    public ReplyComplain save(ReplyComplain replyComplain) {
        return replyComplainRepository.save(replyComplain);
    }

    @Override
    public Page<ResponseReplyComplainListDTO> findReplyComplainList(Pageable pageable) {
        return replyComplainDSLRepository.findReplyComplainList(pageable);
    }

    @Override
    public ReplyComplain findByIdWithJoin(Long id) {
        return replyComplainDSLRepository.findById(id)
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_COMPLAIN_NOT_FOUND));
    }

    @Override
    public Page<ResponseReplyComplainListDTO> findAllByUser(Long userId, Pageable pageable) {
        return replyComplainDSLRepository.findAllByUser(userId, pageable);
    }

    @Override
    public void delete(ReplyComplain replyComplain) {
        replyComplainRepository.delete(replyComplain);
    }
}
