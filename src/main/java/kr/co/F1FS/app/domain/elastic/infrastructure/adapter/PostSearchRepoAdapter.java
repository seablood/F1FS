package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.PostSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSearchRepoAdapter implements PostSearchRepoPort {
    private final PostSearchRepository postSearchRepository;

    @Override
    public void save(PostDocument document) {
        postSearchRepository.save(document);
    }

    @Override
    public PostDocument findById(Long id) {
        return postSearchRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.SEARCH_ERROR_POST));
    }

    @Override
    public void delete(PostDocument document) {
        postSearchRepository.delete(document);
    }
}
