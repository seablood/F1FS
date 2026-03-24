package kr.co.F1FS.app.domain.reply.application.port.in.replyLike;

public interface CheckReplyLikeRelationUseCase {
    boolean existsByUserAndReply(Long userId, Long replyId);
}
