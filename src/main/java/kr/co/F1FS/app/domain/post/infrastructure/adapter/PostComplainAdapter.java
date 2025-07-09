package kr.co.F1FS.app.domain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.infrastructure.repository.PostRepository;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostComplainAdapter implements PostComplainPort {
    private final PostRepository postRepository;

    @Override
    public Post findByIdNotDTO(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }
}
