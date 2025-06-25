package kr.co.F1FS.app.domain.reply.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.application.port.out.ReplyFCMLivePort;
import kr.co.F1FS.app.domain.reply.application.port.out.ReplyNotificationRedisPort;
import kr.co.F1FS.app.domain.reply.application.port.out.ReplyPostPort;
import kr.co.F1FS.app.global.util.FCMUtil;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.CreateReplyDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.ModifyReplyDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.reply.infrastructure.ReplyRepository;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.application.service.ValidationService;
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
    private final ReplyPostPort postPort;
    private final ReplyFCMLivePort fcmLivePort;
    private final ReplyNotificationRedisPort redisPort;
    private final ReplyMapper replyMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final FCMUtil fcmUtil;

    @Transactional
    public Reply save(CreateReplyDTO dto, User user, Long id){
        Post post = postPort.findByIdNotDTO(id);
        Reply reply = replyMapper.toReply(dto, user, post);
        validationService.checkValid(reply);

        pushNotification(user, reply, post, id);
        return replyRepository.save(reply);
    }

    @Cacheable(value = "PostReplyList", key = "#id", cacheManager = "redisLongCacheManager")
    public List<ResponseReplyDTO> findByPost(Long id){
        Post post = postPort.findByIdNotDTO(id);
        List<ResponseReplyDTO> dtoList = replyRepository.findAllByPost(post).stream()
                .map(reply -> replyMapper.toResponseReplyDTO(reply))
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
        return replyMapper.toResponseReplyDTO(reply);
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

    public void pushNotification(User user, Reply reply, Post post, Long id){
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            if(redisPort.isSubscribe(post.getAuthor().getId(), "reply")){
                FCMPushDTO pushDTO = fcmUtil.sendPushForReply(user, reply.getContent());
                FCMToken token = fcmUtil.getAuthorToken(post.getAuthor());
                fcmLivePort.sendPushForAuthor(pushDTO, token, post.getAuthor(), id);
            }
        }
    }
}
