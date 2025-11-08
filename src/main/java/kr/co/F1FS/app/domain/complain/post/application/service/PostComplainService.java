package kr.co.F1FS.app.domain.complain.post.application.service;

import kr.co.F1FS.app.domain.complain.post.application.mapper.PostComplainMapper;
import kr.co.F1FS.app.domain.complain.post.application.port.in.PostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.post.application.port.in.PostUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.CreatePostComplainDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostComplainService implements PostComplainUseCase {
    private final PostUseCase postUseCase;
    public final PostComplainJpaPort postComplainJpaPort;
    private final PostComplainMapper complainMapper;
    private final SlackService slackService;

    @Override
    public void save(PostComplain complain){
        postComplainJpaPort.save(complain);
    }

    @Override
    @Transactional
    public void postComplain(Long id, User user, CreatePostComplainDTO dto){
        Post post = postUseCase.findByIdNotDTONotCache(id);
        PostComplain complain = complainMapper.toPostComplain(post, user, dto);

        save(complain);
        sendMessage(complain);
        log.info("게시글 신고 완료 : {}", post.getTitle());
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
