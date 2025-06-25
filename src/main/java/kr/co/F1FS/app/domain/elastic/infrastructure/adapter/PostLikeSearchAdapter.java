package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.service.PostSearchService;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.application.port.out.PostLikeSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeSearchAdapter implements PostLikeSearchPort {
    private final PostSearchService searchService;

    @Override
    public void save(PostDocument postDocument) {
        searchService.save(postDocument);
    }

    @Override
    public PostDocument findById(Long id) {
        return searchService.findById(id);
    }

    @Override
    public void increaseLikeNum(PostDocument postDocument) {
        searchService.increaseLikeNum(postDocument);
    }

    @Override
    public void decreaseLikeNum(PostDocument postDocument) {
        searchService.decreaseLikeNum(postDocument);
    }
}
