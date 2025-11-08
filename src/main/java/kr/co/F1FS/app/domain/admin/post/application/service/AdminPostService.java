package kr.co.F1FS.app.domain.admin.post.application.service;

import kr.co.F1FS.app.domain.admin.post.application.port.in.AdminPostUseCase;
import kr.co.F1FS.app.domain.admin.post.application.port.out.AdminPostPort;
import kr.co.F1FS.app.domain.admin.post.presentation.dto.AdminResponsePostComplainDTO;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.post.application.mapper.PostMapper;
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
    private final PostComplainJpaPort complainJpaPort;
    private final AdminPostPort postPort;
    private final PostMapper postMapper;
    private final CacheEvictUtil cacheEvictUtil;

    public Page<ResponseSimplePostDTO> getPostByUser(int page, int size, String condition, String nickname){
        Pageable pageable = switchCondition(page, size, condition);
        User user = userUseCase.findByNicknameNotDTO(nickname);
        Page<Post> postPage = postPort.findAllByAuthor(user, pageable);

        return postPage.map(post -> postMapper.toResponseSimplePostDTO(post));
    }

    public Page<AdminResponsePostComplainDTO> getAllComplain(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return complainJpaPort.findAll(pageable);
    }

    @Transactional
    public void delete(Long id){
        Post post = postPort.findByIdNotDTO(id);
        cacheEvictUtil.evictCachingPost(post);

        postPort.delete(post);
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
