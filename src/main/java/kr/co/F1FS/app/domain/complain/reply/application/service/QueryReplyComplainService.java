package kr.co.F1FS.app.domain.complain.reply.application.service;

import kr.co.F1FS.app.domain.complain.reply.application.mapper.ReplyComplainMapper;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.QueryReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.out.ReplyComplainJpaPort;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryReplyComplainService implements QueryReplyComplainUseCase {
    private final ReplyComplainJpaPort replyComplainJpaPort;
    private final ReplyComplainMapper replyComplainMapper;

    @Override
    public Page<ResponseReplyComplainListDTO> findReplyComplainListForDTO(Pageable pageable) {
        return replyComplainJpaPort.findReplyComplainList(pageable);
    }

    @Override
    public ReplyComplain findByIdWithJoin(Long id) {
        return replyComplainJpaPort.findByIdWithJoin(id);
    }

    @Override
    public ResponseReplyComplainDTO findByIdForDTO(Long id) {
        return replyComplainMapper.toResponseReplyComplainDTO(replyComplainJpaPort.findByIdWithJoin(id));
    }

    @Override
    public Page<ResponseReplyComplainListDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return replyComplainJpaPort.findAllByUser(userId, pageable);
    }
}
