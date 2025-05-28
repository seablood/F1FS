package kr.co.F1FS.app.application.complain.post;

import kr.co.F1FS.app.domain.model.rdb.PostComplain;
import kr.co.F1FS.app.domain.repository.rdb.complain.PostComplainRepository;
import kr.co.F1FS.app.presentation.admin.post.dto.ResponsePostComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostComplainService {
    private final PostComplainRepository complainRepository;

    public void save(PostComplain complain){
        complainRepository.save(complain);
    }

    public Page<ResponsePostComplainDTO> findAll(Pageable pageable){
        return complainRepository.findAll(pageable).map(complain -> ResponsePostComplainDTO.toDto(complain));
    }
}
