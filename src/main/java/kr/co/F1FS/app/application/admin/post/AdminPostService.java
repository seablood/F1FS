package kr.co.F1FS.app.application.admin.post;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.user.UserService;
import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.post.PostRepository;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import kr.co.F1FS.app.presentation.post.dto.ResponsePostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminPostService {
    private final UserService userService;
    private final PostRepository postRepository;
    private final CacheEvictUtil cacheEvictUtil;

    public Page<ResponsePostDTO> getPostByUser(int page, int size, String condition, String nickname){
        Pageable pageable = switchCondition(page, size, condition);
        User user = userService.findByNicknameNotDTO(nickname);
        Page<Post> postPage = postRepository.findAllByAuthor(user, pageable);

        return postPage.map(post -> ResponsePostDTO.toDto(post));
    }

    @Transactional
    public void delete(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        cacheEvictUtil.evictCachingPost(post);

        postRepository.delete(post);
        log.info("관리자 권한 -> 게시글 삭제 : {}", post.getTitle());
    }

    public Pageable switchCondition(int page, int size, String condition){
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }
}
