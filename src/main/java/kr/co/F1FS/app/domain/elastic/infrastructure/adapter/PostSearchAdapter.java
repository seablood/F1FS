package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.service.PostSearchService;
import kr.co.F1FS.app.domain.post.application.port.out.PostSearchPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSearchAdapter implements PostSearchPort {
    private final PostSearchService postSearchService;

    @Override
    public void save(Post post) {
        postSearchService.save(post);
    }
}
