package kr.co.F1FS.app.domain.post.application.port.out.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostJpaPort {
    Post save(Post post);
    Post saveAndFlush(Post post);
    Page<ResponsePostListDTO> findPostList(Pageable pageable);
    Page<ResponsePostListDTO> findAllByAuthor(Long authorId, Pageable pageable);
    Page<ResponsePostListDTO> findAllByPostRoom(Long postRoomId, Pageable pageable);
    List<Post> findAllByPostRoom(PostRoom postRoom);
    Post findById(Long id);
    Post findPostById(Long id);
    Post findByIdWithJoin(Long id);
    void delete(Post post);
}
