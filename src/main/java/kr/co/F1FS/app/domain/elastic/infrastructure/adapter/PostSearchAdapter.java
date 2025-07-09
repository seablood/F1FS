package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.domain.post.application.port.out.PostSearchPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSearchAdapter implements PostSearchPort {
    private final PostSearchRepository searchRepository;

    @Override
    public void save(Post post) {
        PostDocument postDocument = PostDocument.builder()
                .post(post).build();

        searchRepository.save(postDocument);
    }
}
