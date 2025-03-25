package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreateReplyDTO;
import kr.co.F1FS.app.dto.ModifyReplyDTO;
import kr.co.F1FS.app.dto.ResponseReplyDTO;
import kr.co.F1FS.app.model.Post;
import kr.co.F1FS.app.model.Reply;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.ReplyRepository;
import kr.co.F1FS.app.util.AuthorCertification;
import kr.co.F1FS.app.util.CacheEvictUtil;
import kr.co.F1FS.app.util.ValidationService;
import kr.co.F1FS.app.util.reply.ReplyException;
import kr.co.F1FS.app.util.reply.ReplyExceptionType;
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
    private final CacheEvictUtil cacheEvictUtil;

    @Transactional
    public Reply save(CreateReplyDTO dto, User user, Long id){
        Post post = postService.findByIdNotDTO(id);
        Reply reply = CreateReplyDTO.toEntity(dto, post, user);
        validationService.checkValid(reply);

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
