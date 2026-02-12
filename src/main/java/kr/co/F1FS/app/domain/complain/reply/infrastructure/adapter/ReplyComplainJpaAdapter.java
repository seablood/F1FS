package kr.co.F1FS.app.domain.complain.reply.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.reply.application.port.out.ReplyComplainJpaPort;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.infrastructure.repository.ReplyComplainRepository;
import kr.co.F1FS.app.domain.user.domain.User;
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

    @Override
    public ReplyComplain save(ReplyComplain replyComplain) {
        return replyComplainRepository.save(replyComplain);
    }

    @Override
    public Page<ReplyComplain> findAll(Pageable pageable) {
        return replyComplainRepository.findAll(pageable);
    }

    @Override
    public ReplyComplain findById(Long id) {
        return replyComplainRepository.findById(id)
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_COMPLAIN_NOT_FOUND));
    }

    @Override
    public Page<ReplyComplain> findAllByFromUser(User user, Pageable pageable) {
        return replyComplainRepository.findAllByFromUser(user, pageable);
    }

    @Override
    public void delete(ReplyComplain replyComplain) {
        replyComplainRepository.delete(replyComplain);
    }
}
