package kr.co.F1FS.app.domain.post.application.port.in.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostUseCase {
    Post findById(Long id);
    ResponsePostDTO findByIdForDTO(Long id);
    Page<ResponseSimplePostDTO> findAll(Pageable pageable);
    Page<ResponseSimplePostDTO> findAllByAuthor(User user, Pageable pageable);
}
