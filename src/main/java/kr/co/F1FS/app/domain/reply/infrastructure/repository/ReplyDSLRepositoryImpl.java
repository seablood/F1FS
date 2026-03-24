package kr.co.F1FS.app.domain.reply.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.post.domain.QPost;
import kr.co.F1FS.app.domain.reply.domain.QReply;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl.ReplyDSLRepository;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.QResponseReplyListByUserDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.QResponseReplyListDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListByUserDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.SoftDeleteExpressions;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReplyDSLRepositoryImpl implements ReplyDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Reply> findByIdForPost(Long id) {
        QReply reply = QReply.reply;
        QUser author = new QUser("author");
        QPost post = new QPost("post");

        Reply content = queryFactory
                .selectFrom(reply)
                .join(reply.user, author).fetchJoin()
                .join(reply.post, post).fetchJoin()
                .where(reply.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public List<ResponseReplyListDTO> findAllByPost(Long postId) {
        QReply reply = QReply.reply;
        QUser author = new QUser("author");

        List<ResponseReplyListDTO> contents = queryFactory
                .select(new QResponseReplyListDTO(
                        reply.id,
                        author.nickname,
                        reply.content,
                        reply.createdAt,
                        reply.updatedAt
                ))
                .from(reply)
                .join(reply.user, author).fetchJoin()
                .where(reply.post.id.eq(postId))
                .fetch();

        return contents;
    }

    @Override
    public Page<ResponseReplyListByUserDTO> findAllByUser(Long userId, Pageable pageable) {
        QReply reply = QReply.reply;
        QUser author = new QUser("author");
        QPost post = new QPost("post");

        List<ResponseReplyListByUserDTO> contents = queryFactory
                .select(new QResponseReplyListByUserDTO(
                        post.id,
                        author.nickname,
                        reply.content,
                        reply.createdAt,
                        reply.updatedAt
                ))
                .from(reply)
                .join(reply.user, author)
                .join(reply.post, post)
                .where(reply.user.id.eq(userId), SoftDeleteExpressions.isNotDeleted(post), SoftDeleteExpressions.isNotDeleted(reply))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(reply.count())
                .from(reply)
                .where(reply.user.id.eq(userId), SoftDeleteExpressions.isNotDeleted(post), SoftDeleteExpressions.isNotDeleted(reply))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QReply reply = QReply.reply;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, reply.createdAt);
                        default ->
                            throw new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
