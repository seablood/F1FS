package kr.co.F1FS.app.domain.complain.post.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.domain.QPostComplain;
import kr.co.F1FS.app.domain.complain.post.infrastructure.repository.dsl.PostComplainDSLRepository;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.QResponsePostComplainListDTO;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import kr.co.F1FS.app.domain.post.domain.QPost;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.SoftDeleteExpressions;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostComplainDSLRepositoryImpl implements PostComplainDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostComplain> findById(Long id) {
        QPostComplain postComplain = QPostComplain.postComplain;
        QPost post = new QPost("post");
        QUser user = new QUser("user");

        PostComplain content = queryFactory
                .selectFrom(postComplain)
                .join(postComplain.toPost, post).fetchJoin()
                .join(postComplain.fromUser, user).fetchJoin()
                .where(postComplain.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponsePostComplainListDTO> findPostComplainList(Pageable pageable) {
        QPostComplain postComplain = QPostComplain.postComplain;
        QPost post = QPost.post;

        List<ResponsePostComplainListDTO> contents = queryFactory
                .select(new QResponsePostComplainListDTO(
                        postComplain.id,
                        post.id,
                        postComplain.description
                ))
                .from(postComplain)
                .join(postComplain.toPost, post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(postComplain.count())
                .from(postComplain)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponsePostComplainListDTO> findAllByUser(Long userId, Pageable pageable) {
        QPostComplain postComplain = QPostComplain.postComplain;
        QPost post = QPost.post;

        List<ResponsePostComplainListDTO> contents = queryFactory
                .select(new QResponsePostComplainListDTO(
                        postComplain.id,
                        post.id,
                        postComplain.description
                ))
                .from(postComplain)
                .join(postComplain.toPost, post)
                .where(postComplain.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(postComplain.count())
                .from(postComplain)
                .where(postComplain.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(post))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QPostComplain postComplain = QPostComplain.postComplain;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, postComplain.createdAt);
                        default ->
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
