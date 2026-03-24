package kr.co.F1FS.app.domain.complain.post.application.service;

import kr.co.F1FS.app.domain.complain.post.application.mapper.PostComplainMapper;
import kr.co.F1FS.app.domain.complain.post.application.port.in.QueryPostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.post.ResponsePostComplainDTO;
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
    public PostComplain findByIdWithJoin(Long id) {
        return postComplainJpaPort.findByIdWithJoin(id);
    }

    @Override
    public Page<ResponsePostComplainListDTO> findPostComplainListForDTO(Pageable pageable) {
        return postComplainJpaPort.findPostComplainList(pageable);
    }

    @Override
    public ResponsePostComplainDTO findByIdForDTO(Long id) {
        return postComplainMapper.toResponsePostComplainDTO(postComplainJpaPort.findByIdWithJoin(id));
    }

    @Override
    public Page<ResponsePostComplainListDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return postComplainJpaPort.findAllByUser(userId, pageable);
    }
}
