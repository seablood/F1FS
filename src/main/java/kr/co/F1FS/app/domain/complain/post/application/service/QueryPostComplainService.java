package kr.co.F1FS.app.domain.complain.post.application.service;

import kr.co.F1FS.app.domain.complain.post.application.mapper.PostComplainMapper;
import kr.co.F1FS.app.domain.complain.post.application.port.in.QueryPostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponsePostComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostComplainService implements QueryPostComplainUseCase {
    private final PostComplainJpaPort postComplainJpaPort;
    private final PostComplainMapper postComplainMapper;

    @Override
    public PostComplain findById(Long id) {
        return postComplainJpaPort.findById(id);
    }

    @Override
    public Page<PostComplain> findAll(Pageable pageable) {
        return postComplainJpaPort.findAll(pageable);
    }

    @Override
    public ResponsePostComplainDTO findByIdForDTO(Long id) {
        return postComplainMapper.toResponsePostComplainDTO(postComplainJpaPort.findById(id));
    }

    @Override
    public Page<ResponsePostComplainDTO> findAllByUserForDTO(User user, Pageable pageable) {
        return postComplainJpaPort.findAllByUser(user, pageable)
                .map(postComplain -> postComplainMapper.toResponsePostComplainDTO(postComplain));
    }
}
