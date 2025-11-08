package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.domain.post.application.port.out.PostSearchPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
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

    @Override
    public void save(PostDocument document) {
        searchRepository.save(document);
    }

    @Override
    public PostDocument findById(Long id) {
        return searchRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }

    @Override
    public void delete(PostDocument document) {
        searchRepository.delete(document);
    }
}
