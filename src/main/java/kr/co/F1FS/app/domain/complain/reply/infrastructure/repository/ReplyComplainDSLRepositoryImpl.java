package kr.co.F1FS.app.domain.complain.reply.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.complain.reply.domain.QReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.infrastructure.repository.dsl.ReplyComplainDSLRepository;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.QResponseReplyComplainListDTO;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.domain.reply.domain.QReply;
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
public class ReplyComplainDSLRepositoryImpl implements ReplyComplainDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ReplyComplain> findById(Long id) {
        QReplyComplain replyComplain = QReplyComplain.replyComplain;
        QReply reply = new QReply("reply");
        QUser user = new QUser("user");

        ReplyComplain content = queryFactory
                .selectFrom(replyComplain)
                .join(replyComplain.toReply, reply).fetchJoin()
                .join(replyComplain.fromUser, user).fetchJoin()
                .where(replyComplain.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponseReplyComplainListDTO> findReplyComplainList(Pageable pageable) {
        QReplyComplain replyComplain = QReplyComplain.replyComplain;
        QReply reply = QReply.reply;

        List<ResponseReplyComplainListDTO> contents = queryFactory
                .select(new QResponseReplyComplainListDTO(
                        replyComplain.id,
                        reply.content,
                        replyComplain.description
                ))
                .from(replyComplain)
                .join(replyComplain.toReply, reply)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(replyComplain.count())
                .from(replyComplain)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponseReplyComplainListDTO> findAllByUser(Long userId, Pageable pageable) {
        QReplyComplain replyComplain = QReplyComplain.replyComplain;
        QReply reply = QReply.reply;

        List<ResponseReplyComplainListDTO> contents = queryFactory
                .select(new QResponseReplyComplainListDTO(
                        replyComplain.id,
                        reply.content,
                        replyComplain.description
                ))
                .from(replyComplain)
                .join(replyComplain.toReply, reply)
                .where(replyComplain.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(reply))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(replyComplain.count())
                .from(replyComplain)
                .where(replyComplain.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(reply))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QReplyComplain replyComplain = QReplyComplain.replyComplain;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, replyComplain.createdAt);
                        default ->
                            throw new ReplyException(ReplyExceptionType.REPLY_COMPLAIN_NOT_FOUND);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
