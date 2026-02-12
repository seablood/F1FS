package kr.co.F1FS.app.domain.complain.reply.application.service;

import kr.co.F1FS.app.domain.complain.reply.application.port.in.CreateReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.out.ReplyComplainJpaPort;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.CreateReplyComplainDTO;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateReplyComplainService implements CreateReplyComplainUseCase {
    private final ReplyComplainJpaPort replyComplainJpaPort;
    private final ReplyComplainDomainService replyComplainDomainService;

    @Override
    public ReplyComplain save(Reply reply, User user, CreateReplyComplainDTO dto) {
        ReplyComplain replyComplain = replyComplainDomainService.createEntity(reply, user, dto);

        return replyComplainJpaPort.save(replyComplain);
    }
}
