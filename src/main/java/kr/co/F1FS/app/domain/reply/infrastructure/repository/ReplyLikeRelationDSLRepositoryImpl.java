package kr.co.F1FS.app.domain.reply.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.reply.domain.QReplyLikeRelation;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl.ReplyLikeRelationDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyLikeRelationDSLRepositoryImpl implements ReplyLikeRelationDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public ReplyLikeRelation findByUserAndReply(Long userId, Long replyId) {
        QReplyLikeRelation relation = QReplyLikeRelation.replyLikeRelation;

        ReplyLikeRelation content = queryFactory
                .selectFrom(relation)
                .where(relation.user.id.eq(userId), relation.reply.id.eq(replyId))
                .fetchOne();

        return content;
    }

    @Override
    public boolean existsByUserAndReply(Long userId, Long replyId) {
        QReplyLikeRelation relation = QReplyLikeRelation.replyLikeRelation;

        return queryFactory
                .selectOne()
                .from(relation)
                .where(relation.user.id.eq(userId), relation.reply.id.eq(replyId))
                .fetchOne() != null;
    }
}
