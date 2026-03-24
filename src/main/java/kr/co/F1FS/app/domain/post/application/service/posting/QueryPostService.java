package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.QueryPostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.application.mapper.posting.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.posting.PostJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostService implements QueryPostUseCase {
    private final PostJpaPort postJpaPort;
    private final QueryPostSearchUseCase queryPostSearchUseCase;
    private final PostMapper postMapper;

    @Override
    public Post findById(Long id) {
        return postJpaPort.findById(id);
    }

    @Override
    public Post findPostById(Long id) {
        return postJpaPort.findPostById(id);
    }

    @Override
    public Post findByIdWithJoin(Long id) {
        return postJpaPort.findByIdWithJoin(id);
    }

    @Override
    public ResponsePostDTO findByIdForDTO(Long id) {
        Post post = postJpaPort.findById(id);
        PostDocument document = queryPostSearchUseCase.findById(id);

        return postMapper.toResponsePostDTO(post, document.getTags());
    }

    @Override
    public Page<ResponsePostListDTO> findPostListForDTO(Pageable pageable) {
        return postJpaPort.findPostList(pageable);
    }

    @Override
    public Page<ResponsePostListDTO> findAllByAuthorForDTO(Long authorId, Pageable pageable) {
        return postJpaPort.findAllByAuthor(authorId, pageable);
    }
}
