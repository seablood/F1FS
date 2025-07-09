package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeSearchAdapter implements PostLikeSearchPort {
    private final PostSearchRepository searchRepository;

    @Override
    public void save(PostDocument postDocument) {
        searchRepository.save(postDocument);
    }

    @Override
    public PostDocument findById(Long id) {
        return searchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
    }
}
