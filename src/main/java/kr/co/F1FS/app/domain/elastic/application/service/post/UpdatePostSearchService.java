package kr.co.F1FS.app.domain.elastic.application.service.post;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.UpdatePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostSearchService implements UpdatePostSearchUseCase {
    private final PostSearchRepository postSearchRepository;
    private final PostSearchRepoPort postSearchRepoPort;
    private final PostSearchDocumentService postSearchDocumentService;

    @Override
    public void modify(PostDocument document, Post post) {
        postSearchDocumentService.modify(document, post);

        postSearchRepoPort.save(document);
    }

    @Override
    public void increaseLikeNum(PostDocument document) {
        postSearchDocumentService.increaseLikeNum(document);

        postSearchRepoPort.save(document);
    }

    @Override
    public void decreaseLikeNum(PostDocument document) {
        postSearchDocumentService.decreaseLikeNum(document);

        postSearchRepoPort.save(document);
    }
}
