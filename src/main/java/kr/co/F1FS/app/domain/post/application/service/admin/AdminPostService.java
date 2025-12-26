package kr.co.F1FS.app.domain.post.application.service.admin;

import kr.co.F1FS.app.domain.post.application.port.in.admin.AdminPostUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.posting.DeletePostUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
    private final DeletePostUseCase deletePostUseCase;
    private final QueryPostUseCase queryPostUseCase;
    private final QueryUserUseCase queryUserUseCase;

    @Override
    public Page<ResponseSimplePostDTO> getPostByUser(int page, int size, String condition, Long id) {
        Pageable pageable = switchCondition(page, size, condition);
        User user = queryUserUseCase.findById(id);

        return queryPostUseCase.findAllByAuthor(user, pageable);
    }

    @Override
    @Cacheable(value = "PostDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostDTO getPostById(Long id) {
        return queryPostUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Post post = queryPostUseCase.findById(id);

        deletePostUseCase.delete(post);
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
