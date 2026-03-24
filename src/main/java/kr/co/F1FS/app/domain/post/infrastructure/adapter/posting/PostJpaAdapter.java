package kr.co.F1FS.app.domain.post.infrastructure.adapter.posting;

import kr.co.F1FS.app.domain.post.application.port.out.posting.PostJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.infrastructure.repository.PostRepository;
import kr.co.F1FS.app.domain.post.infrastructure.repository.dsl.PostDSLRepository;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostJpaAdapter implements PostJpaPort {
    private final PostRepository postRepository;
    private final PostDSLRepository postDSLRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post saveAndFlush(Post post) {
        return postRepository.saveAndFlush(post);
    }

    @Override
    public Page<ResponsePostListDTO> findPostList(Pageable pageable) {
        return postDSLRepository.findPostList(pageable);
    }

    @Override
    public Page<ResponsePostListDTO> findAllByAuthor(Long authorId, Pageable pageable) {
        return postDSLRepository.findAllByAuthor(authorId, pageable);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findPostById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }

    @Override
    public Post findByIdWithJoin(Long id) {
        return postDSLRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
