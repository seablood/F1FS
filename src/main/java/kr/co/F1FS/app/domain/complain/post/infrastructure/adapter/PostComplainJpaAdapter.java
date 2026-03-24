package kr.co.F1FS.app.domain.complain.post.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.infrastructure.repository.PostComplainRepository;
import kr.co.F1FS.app.domain.complain.post.infrastructure.repository.dsl.PostComplainDSLRepository;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostComplainJpaAdapter implements PostComplainJpaPort {
    private final PostComplainRepository postComplainRepository;
    private final PostComplainDSLRepository postComplainDSLRepository;

    @Override
    public PostComplain save(PostComplain postComplain) {
        return postComplainRepository.save(postComplain);
    }

    @Override
    public Page<ResponsePostComplainListDTO> findPostComplainList(Pageable pageable) {
        return postComplainDSLRepository.findPostComplainList(pageable);
    }

    @Override
    public Page<ResponsePostComplainListDTO> findAllByUser(Long userId, Pageable pageable) {
        return postComplainDSLRepository.findAllByUser(userId, pageable);
    }

    @Override
    public PostComplain findByIdWithJoin(Long id) {
        return postComplainDSLRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_COMPLAIN_NOT_FOUND));
    }

    @Override
    public void delete(PostComplain postComplain) {
        postComplainRepository.delete(postComplain);
    }
}
