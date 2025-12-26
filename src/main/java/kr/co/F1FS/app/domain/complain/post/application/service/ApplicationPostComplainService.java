package kr.co.F1FS.app.domain.complain.post.application.service;

import kr.co.F1FS.app.domain.complain.post.application.port.in.CreatePostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.in.DeletePostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.in.PostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.in.QueryPostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.SlackService;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponsePostComplainDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationPostComplainService implements PostComplainUseCase {
    private final CreatePostComplainUseCase createPostComplainUseCase;
    private final DeletePostComplainUseCase deletePostComplainUseCase;
    private final QueryPostComplainUseCase queryPostComplainUseCase;
    private final QueryPostUseCase queryPostUseCase;
    public final PostComplainJpaPort postComplainJpaPort;
    private final SlackService slackService;

    @Override
    @Transactional
    public void postComplain(Long id, User user, CreatePostComplainDTO dto){
        Post post = queryPostUseCase.findById(id);
        PostComplain complain = createPostComplainUseCase.save(post, user, dto);

        sendMessage(complain);
        log.info("게시글 신고 완료 : {}", post.getTitle());
    }

    @Override
    public Page<ResponsePostComplainDTO> findAllByUser(int page, int size, String condition, User user) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryPostComplainUseCase.findAllByUserForDTO(user, pageable);
    }

    @Override
    @Cacheable(value = "PostComplainDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostComplainDTO getPostComplain(Long id) {
        return queryPostComplainUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional
    public void delete(Long id, User user) {
        PostComplain postComplain = queryPostComplainUseCase.findById(id);

        deletePostComplainUseCase.delete(postComplain, user);
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

    public void sendMessage(PostComplain complain){
        String title = "게시글 신고가 접수되었습니다.";
        HashMap<String, String> data = new HashMap<>();
        data.put("신고자", complain.getFromUser().getNickname());
        data.put("신고된 게시글", complain.getToPost().getTitle());
        data.put("신고 사유", complain.getDescription());

        slackService.sendComplainMessage(title, data);
    }
}
