package kr.co.F1FS.app.domain.post.application.port.in.posting;

import kr.co.F1FS.app.domain.post.presentation.dto.CreatePostBlockRequestDTO;
import kr.co.F1FS.app.domain.post.presentation.dto.ModifyPostBlockRequestDTO;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;

public interface PostUseCase {
    ResponsePostDTO save(CreatePostBlockRequestDTO requestDTO, User author);
    Page<ResponsePostListDTO> getPostAll(int page, int size, String condition);
    Page<ResponsePostListDTO> getPostListByUser(int page, int size, String condition, User user);
    ResponsePostDTO getPostById(Long id);
    ResponsePostDTO modify(Long id, ModifyPostBlockRequestDTO requestDTO, User user);
    void delete(Long id, User user);
}
