package kr.co.F1FS.app.domain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainPort;
import kr.co.F1FS.app.domain.post.application.service.PostService;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostComplainAdapter implements PostComplainPort {
    private final PostService postService;

    @Override
    public Post findByIdNotDTO(Long id) {
        return postService.findByIdNotDTO(id);
    }
}
