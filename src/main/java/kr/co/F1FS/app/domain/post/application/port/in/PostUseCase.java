package kr.co.F1FS.app.domain.post.application.port.in;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.CreatePostDTO;
import kr.co.F1FS.app.domain.post.presentation.dto.ModifyPostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostUseCase {
    ResponsePostDTO save(CreatePostDTO dto, User author);
    Page<ResponseSimplePostDTO> findAll(int page, int size, String condition);
    ResponsePostDTO findById(Long id);
    Post findByIdNotDTO(Long id);
    Post findByIdNotDTONotCache(Long id);
    Page<Post> findAllByAuthor(User user, Pageable pageable);
    ResponsePostDTO modify(Long id, ModifyPostDTO dto, User user);
    void increaseLike(Post post);
    void decreaseLike(Post post);
    void delete(Long id, User user);
    void delete(Post post);
    ResponseSimplePostDTO toResponseSimplePostDTO(Post post);
    Pageable conditionSwitch(int page, int size, String condition);
}
