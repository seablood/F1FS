package kr.co.F1FS.app.domain.complain.post.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.complain.post.application.port.in.PostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.infrastructure.repository.PostComplainRepository;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostComplainService implements PostComplainUseCase {
    private final PostComplainPort postPort;
    private final SlackService slackService;
    private final PostComplainRepository complainRepository;

    public void save(PostComplain complain){
        complainRepository.save(complain);
    }

    @Transactional
    public void postComplain(Long id, User user, CreatePostComplainDTO dto){
        Post post = postPort.findByIdNotDTO(id);
        PostComplain complain = PostComplain.builder()
                .toPost(post)
                .fromUser(user)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();

        save(complain);
        sendMessage(complain);
        log.info("게시글 신고 완료 : {}", post.getTitle());
    }

    public Page<PostComplain> findAll(Pageable pageable){
        return complainRepository.findAll(pageable);
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
