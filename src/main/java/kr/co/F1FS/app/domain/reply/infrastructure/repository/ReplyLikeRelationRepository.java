package kr.co.F1FS.app.domain.reply.infrastructure.repository;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyLikeRelationRepository extends JpaRepository<ReplyLikeRelation, Long> {
    ReplyLikeRelation findReplyLikeRelationByUserAndReply(User user, Reply reply);
    boolean existsReplyLikeRelationByUserAndReply(User user, Reply reply);
}
