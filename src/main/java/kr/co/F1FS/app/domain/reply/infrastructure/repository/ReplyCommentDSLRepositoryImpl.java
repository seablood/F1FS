package kr.co.F1FS.app.domain.reply.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.reply.domain.QReply;
import kr.co.F1FS.app.domain.reply.domain.QReplyComment;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl.ReplyCommentDSLRepository;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.QResponseReplyCommentListDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ResponseReplyCommentListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReplyCommentDSLRepositoryImpl implements ReplyCommentDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ReplyComment> findById(Long id) {
        QReplyComment comment = QReplyComment.replyComment;
        QUser author = new QUser("author");
        QReply reply = new QReply("reply");

        ReplyComment content = queryFactory
                .selectFrom(comment)
                .join(comment.user, author).fetchJoin()
                .join(comment.reply, reply).fetchJoin()
                .where(comment.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public List<ResponseReplyCommentListDTO> findAllByReply(Long replyId) {
        QReplyComment comment = QReplyComment.replyComment;
        QUser author = new QUser("author");

        List<ResponseReplyCommentListDTO> contents = queryFactory
                .select(new QResponseReplyCommentListDTO(
                        comment.id,
                        author.nickname,
                        comment.content,
                        comment.createdAt,
                        comment.updatedAt
                ))
                .from(comment)
                .join(comment.user, author).fetchJoin()
                .where(comment.reply.id.eq(replyId))
                .fetch();

        return contents;
    }
}
