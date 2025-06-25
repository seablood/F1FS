package kr.co.F1FS.app.domain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.post.application.service.PostService;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.application.port.out.ReplyPostPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyPostAdapter implements ReplyPostPort {
    private final PostService postService;

    @Override
    public Post findByIdNotDTO(Long id) {
        return postService.findByIdNotDTO(id);
    }
}
