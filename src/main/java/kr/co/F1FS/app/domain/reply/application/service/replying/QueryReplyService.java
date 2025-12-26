package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.QueryReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replying.ReplyJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyByUserDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueryReplyService implements QueryReplyUseCase {
    private final ReplyJpaPort replyJpaPort;
    private final ReplyMapper replyMapper;

    @Override
    public Reply findById(Long id) {
        return replyJpaPort.findById(id);
    }

    @Override
    public List<Reply> findAllByPost(Post post) {
        return replyJpaPort.findAllByPost(post);
    }

    @Override
    public List<ResponseReplyDTO> findReplyList(Post post, List<Reply> replies, Map<Long, List<ResponseReplyCommentDTO>> commentMap) {
        return replies.stream().map(reply -> {
            ResponseReplyDTO dto = replyMapper.toResponseReplyDTO(reply);
            dto.setComments(commentMap.getOrDefault(commentMap.get(reply.getId()), List.of()));
            return dto;
        }).toList();
    }

    @Override
    public Page<ResponseReplyByUserDTO> findAllByUser(User user, Pageable pageable) {
        Page<Reply> replies = replyJpaPort.findAllByUser(user, pageable);

        return replies.map(reply -> replyMapper.toResponseReplyByUserDTO(reply));
    }
}
