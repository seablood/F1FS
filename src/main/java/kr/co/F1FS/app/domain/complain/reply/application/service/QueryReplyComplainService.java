package kr.co.F1FS.app.domain.complain.reply.application.service;

import kr.co.F1FS.app.domain.complain.reply.application.mapper.ReplyComplainMapper;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.QueryReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.out.ReplyComplainJpaPort;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.SimpleResponseReplyComplainDTO;
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
    public Page<SimpleResponseReplyComplainDTO> findAllForDTO(Pageable pageable) {
        return replyComplainJpaPort.findAll(pageable)
                .map(replyComplain -> replyComplainMapper.toSimpleResponseReplyComplainDTO(replyComplain));
    }

    @Override
    public ReplyComplain findById(Long id) {
        return replyComplainJpaPort.findById(id);
    }

    @Override
    public ResponseReplyComplainDTO findByIdForDTO(Long id) {
        return replyComplainMapper.toResponseReplyComplainDTO(replyComplainJpaPort.findById(id));
    }

    @Override
    public Page<SimpleResponseReplyComplainDTO> findAllByFromUserForDTO(User user, Pageable pageable) {
        return replyComplainJpaPort.findAllByFromUser(user, pageable)
                .map(replyComplain -> replyComplainMapper.toSimpleResponseReplyComplainDTO(replyComplain));
    }
}
