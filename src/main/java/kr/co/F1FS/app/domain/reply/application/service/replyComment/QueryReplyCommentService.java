package kr.co.F1FS.app.domain.reply.application.service.replyComment;

import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.QueryReplyCommentUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyComment.ReplyCommentJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryReplyCommentService implements QueryReplyCommentUseCase {
    private final ReplyCommentJpaPort replyCommentJpaPort;
    private final ReplyMapper replyMapper;

    @Override
    public ReplyComment findById(Long id) {
        return replyCommentJpaPort.findById(id);
    }

    @Override
    public Map<Long, List<ResponseReplyCommentDTO>> findByReply(List<Reply> replies) {
        List<ReplyComment> comments = replyCommentJpaPort.findAllByReply(replies);

        Map<Long, List<ResponseReplyCommentDTO>> commentMap = comments.stream()
                .collect(Collectors.groupingBy(
                        replyComment -> replyComment.getReply().getId(),
                        Collectors.mapping(replyComment ->
                                replyMapper.toResponseReplyCommentDTO(replyComment), Collectors.toList())
                ));

        return commentMap;
    }
}
