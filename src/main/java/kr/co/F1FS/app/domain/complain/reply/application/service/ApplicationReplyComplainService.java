package kr.co.F1FS.app.domain.complain.reply.application.service;

import kr.co.F1FS.app.domain.complain.reply.application.port.in.CreateReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.DeleteReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.QueryReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.in.ReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.CreateReplyComplainDTO;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.QueryReplyUseCase;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.SlackService;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
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
public class ApplicationReplyComplainService implements ReplyComplainUseCase {
    private final CreateReplyComplainUseCase createReplyComplainUseCase;
    private final QueryReplyComplainUseCase queryReplyComplainUseCase;
    private final DeleteReplyComplainUseCase deleteReplyComplainUseCase;
    private final QueryReplyUseCase queryReplyUseCase;
    private final SlackService slackService;

    @Override
    @Transactional
    public void save(Long id, User user, CreateReplyComplainDTO dto) {
        Reply reply = queryReplyUseCase.findByIdForPostWithJoin(id);
        ReplyComplain replyComplain = createReplyComplainUseCase.save(reply, user, dto);

        sendMessage(replyComplain);
        log.info("댓글 신고 완료 : {}", reply.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseReplyComplainListDTO> getReplyComplainListByUser(int page, int size, String condition, User user) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryReplyComplainUseCase.findAllByUserForDTO(user.getId(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "ReplyComplainDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseReplyComplainDTO getReplyComplainById(Long id) {
        return queryReplyComplainUseCase.findByIdForDTO(id);
    }

    @Override
    @Transactional
    public void delete(Long id, User user) {
        ReplyComplain replyComplain = queryReplyComplainUseCase.findByIdWithJoin(id);

        deleteReplyComplainUseCase.delete(replyComplain, user);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition) {
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }

    public void sendMessage(ReplyComplain complain){
        String title = "댓글 신고가 접수되었습니다.";
        HashMap<String, String> data = new HashMap<>();
        data.put("신고자", complain.getFromUser().getNickname());
        data.put("신고된 게시글", complain.getToReply().getContent());
        data.put("신고 사유", complain.getDescription());

        slackService.sendComplainMessage(title, data);
    }
}
