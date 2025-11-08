package kr.co.F1FS.app.domain.reply.infrastructure.adapter;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.application.port.out.ReplyJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.ReplyRepository;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyJpaAdapter implements ReplyJpaPort {
    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;

    @Override
    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }

    @Override
    public Reply saveAndFlush(Reply reply) {
        return replyRepository.saveAndFlush(reply);
    }

    @Override
    public Reply findById(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND));
    }

    @Override
    public List<ResponseReplyDTO> findAllByPost(Post post) {
        return replyRepository.findAllByPost(post).stream()
                .map(reply -> replyMapper.toResponseReplyDTO(reply))
                .toList();
    }

    @Override
    public void delete(Reply reply) {
        replyRepository.delete(reply);
    }
}
