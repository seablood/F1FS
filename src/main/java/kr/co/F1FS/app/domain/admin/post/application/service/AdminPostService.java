package kr.co.F1FS.app.domain.admin.post.application.service;

import kr.co.F1FS.app.domain.admin.post.application.port.in.AdminPostUseCase;
import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.application.port.in.PostComplainUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.PostUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminPostService implements AdminPostUseCase {
    private final UserUseCase userUseCase;
    private final PostComplainUseCase complainUseCase;
    private final PostUseCase postUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public Page<ResponseSimplePostDTO> getPostByUser(int page, int size, String condition, String nickname){
        Pageable pageable = switchCondition(page, size, condition);
        User user = userUseCase.findByNicknameNotDTO(nickname);
        Page<Post> postPage = postUseCase.findAllByAuthor(user, pageable);

        return postPage.map(post -> postUseCase.toResponseSimplePostDTO(post));
    }

    @Override
    public Page<AdminResponsePostComplainDTO> getAllComplain(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return complainUseCase.findAll(pageable);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Post post = postUseCase.findByIdNotDTONotCache(id);
        cacheEvictUtil.evictCachingPost(post);

        postUseCase.delete(post);
        log.info("관리자 권한 -> 게시글 삭제 : {}", post.getTitle());
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition){
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }
}
