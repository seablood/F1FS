package kr.co.F1FS.app.domain.elastic.application.service.post;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.CreatePostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostSearchService implements CreatePostSearchUseCase {
    private final PostSearchRepository postSearchRepository;
    private final PostSearchRepoPort postSearchRepoPort;
    private final PostSearchDocumentService postSearchDocumentService;

    @Override
    public void save(Post post, List<String> tags) {
        PostDocument document = postSearchDocumentService.createDocument(post, tags);

        postSearchRepoPort.save(document);
    }
}
