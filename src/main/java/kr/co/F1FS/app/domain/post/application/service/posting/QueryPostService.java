package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.elastic.application.port.in.post.QueryPostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.posting.PostJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
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
    public ResponsePostDTO findByIdForDTO(Long id) {
        Post post = postJpaPort.findById(id);
        PostDocument document = queryPostSearchUseCase.findById(id);

        return postMapper.toResponsePostDTO(post, document.getTags());
    }

    @Override
    public Page<ResponseSimplePostDTO> findAllForDTO(Pageable pageable) {
        return postJpaPort.findAll(pageable).map(post -> postMapper.toResponseSimplePostDTO(post));
    }

    @Override
    public Page<ResponseSimplePostDTO> findAllByAuthorForDTO(User user, Pageable pageable) {
        return postJpaPort.findAllByAuthor(user, pageable).map(post -> postMapper.toResponseSimplePostDTO(post));
    }
}
