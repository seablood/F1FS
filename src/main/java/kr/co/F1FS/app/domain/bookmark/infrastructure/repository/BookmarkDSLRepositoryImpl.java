package kr.co.F1FS.app.domain.bookmark.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.bookmark.domain.QBookmark;
import kr.co.F1FS.app.domain.bookmark.infrastructure.repository.dsl.BookmarkDSLRepository;
import kr.co.F1FS.app.domain.bookmark.presentation.dto.QResponseBookmarkListDTO;
import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import kr.co.F1FS.app.domain.post.domain.QPost;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.SoftDeleteExpressions;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkException;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkDSLRepositoryImpl implements BookmarkDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ResponseBookmarkListDTO> findBookmarkList(Long userId, Pageable pageable) {
        QBookmark bookmark = QBookmark.bookmark;
        QPost post = new QPost("post");
        QUser user = new QUser("author");

        List<ResponseBookmarkListDTO> contents = queryFactory
                .select(new QResponseBookmarkListDTO(
                        post.id,
                        post.title,
                        user.nickname,
                        post.likeNum,
                        post.createdAt
                ))
                .from(bookmark)
                .join(bookmark.post, post)
                .join(bookmark.post.author, user)
                .where(bookmark.user.id.eq(userId), SoftDeleteExpressions.isNotDeleted(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(bookmark.count())
                .from(bookmark)
                .join(bookmark.post, post)
                .where(bookmark.user.id.eq(userId), SoftDeleteExpressions.isNotDeleted(post))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QBookmark bookmark = QBookmark.bookmark;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "markingTime" ->
                            new OrderSpecifier<>(direction, bookmark.markingTime);
                        default ->
                            throw new BookmarkException(BookmarkExceptionType.CONDITION_ERROR_BOOKMARK);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
