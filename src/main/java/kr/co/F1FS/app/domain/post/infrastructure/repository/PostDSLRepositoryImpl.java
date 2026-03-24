package kr.co.F1FS.app.domain.post.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.QPost;
import kr.co.F1FS.app.domain.post.domain.QPostBlock;
import kr.co.F1FS.app.domain.post.infrastructure.repository.dsl.PostDSLRepository;
import kr.co.F1FS.app.domain.post.presentation.dto.QResponsePostListDTO;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
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
public class PostDSLRepositoryImpl implements PostDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Post> findById(Long id) {
        QPost post = QPost.post;
        QUser author = new QUser("author");
        QPostBlock block = new QPostBlock("block");

        Post content = queryFactory
                .selectFrom(post)
                .join(post.author, author).fetchJoin()
                .leftJoin(post.blocks, block).fetchJoin()
                .where(post.id.eq(id), SoftDeleteExpressions.isNotDeleted(post))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponsePostListDTO> findPostList(Pageable pageable) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        List<ResponsePostListDTO> contents = queryFactory
                .select(new QResponsePostListDTO(
                        post.id,
                        post.title,
                        user.nickname,
                        post.createdAt,
                        post.likeNum
                ))
                .from(post)
                .join(post.author, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponsePostListDTO> findAllByAuthor(Long authorId, Pageable pageable) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        List<ResponsePostListDTO> contents = queryFactory
                .select(new QResponsePostListDTO(
                        post.id,
                        post.title,
                        user.nickname,
                        post.createdAt,
                        post.likeNum
                ))
                .from(post)
                .join(post.author, user)
                .where(post.author.id.eq(authorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(post.author.id.eq(authorId))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QPost post = QPost.post;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, post.createdAt);
                        case "likeNum" ->
                            new OrderSpecifier<>(direction, post.likeNum);
                        default ->
                            throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
