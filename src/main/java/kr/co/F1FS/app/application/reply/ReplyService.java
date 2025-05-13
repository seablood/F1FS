package kr.co.F1FS.app.application.reply;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.notification.FCMLiveService;
import kr.co.F1FS.app.application.notification.NotificationRedisService;
import kr.co.F1FS.app.application.post.PostService;
import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import kr.co.F1FS.app.presentation.reply.dto.CreateReplyDTO;
import kr.co.F1FS.app.presentation.reply.dto.ModifyReplyDTO;
import kr.co.F1FS.app.presentation.reply.dto.ResponseReplyDTO;
import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.model.rdb.Reply;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.reply.ReplyRepository;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.application.ValidationService;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final ValidationService validationService;
    private final PostService postService;
    private final FCMLiveService fcmLiveService;
    private final NotificationRedisService redisService;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Transactional
    public Reply save(CreateReplyDTO dto, User user, Long id){
        Post post = postService.findByIdNotDTO(id);
        Reply reply = CreateReplyDTO.toEntity(dto, post, user);
        validationService.checkValid(reply);

        if(redisService.isSubscribe(post.getAuthor().getId(), "reply")){
            FCMPushDTO pushDTO = fcmUtil.sendPushForReply(user, reply.getContent());
            FCMNotification token = fcmUtil.getAuthorToken(post.getAuthor());
            fcmLiveService.sendPushForAuthor(pushDTO, token, id);
        }
        return replyRepository.save(reply);
    }

    @Cacheable(value = "ReplyList", key = "#id", cacheManager = "redisLongCacheManager")
    public List<ResponseReplyDTO> findByPost(Long id){
        Post post = postService.findByIdNotDTO(id);
        List<ResponseReplyDTO> dtoList = replyRepository.findAllByPost(post).stream()
                .map(reply -> ResponseReplyDTO.toDto(reply))
                .toList();

        return dtoList;
    }

    @Transactional
    public ResponseReplyDTO modify(Long replyId, ModifyReplyDTO dto, User user){
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND));
        cacheEvictUtil.evictCachingReply(reply);

        if(!AuthorCertification.certification(user.getUsername(), reply.getUser().getUsername())){
            throw new ReplyException(ReplyExceptionType.NOT_AUTHORITY_UPDATE_POST);
        }

        reply.modify(dto.getContent());
        replyRepository.saveAndFlush(reply);
        return ResponseReplyDTO.toDto(reply);
    }

    @Transactional
    public void delete(Long replyId, User user){
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND));
        if(!AuthorCertification.certification(user.getUsername(), reply.getUser().getUsername())){
            throw new ReplyException(ReplyExceptionType.NOT_AUTHORITY_DELETE_POST);
        }
        cacheEvictUtil.evictCachingReply(reply);

        replyRepository.delete(reply);
    }
}
