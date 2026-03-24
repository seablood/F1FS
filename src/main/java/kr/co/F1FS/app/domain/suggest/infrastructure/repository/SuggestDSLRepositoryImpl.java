package kr.co.F1FS.app.domain.suggest.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.suggest.domain.QSuggest;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.suggest.infrastructure.repository.dsl.SuggestDSLRepository;
import kr.co.F1FS.app.domain.suggest.presentation.dto.QResponseSuggestListDTO;
import kr.co.F1FS.app.domain.suggest.presentation.dto.ResponseSuggestListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.SoftDeleteExpressions;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestException;
import kr.co.F1FS.app.global.util.exception.suggest.SuggestExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SuggestDSLRepositoryImpl implements SuggestDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Suggest> findById(Long id) {
        QSuggest suggest = QSuggest.suggest;
        QUser author = new QUser("author");

        Suggest content = queryFactory
                .selectFrom(suggest)
                .join(suggest.fromUser, author).fetchJoin()
                .where(suggest.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponseSuggestListDTO> findSuggestList(Pageable pageable) {
        QSuggest suggest = QSuggest.suggest;
        QUser author = new QUser("author");

        List<ResponseSuggestListDTO> contents = queryFactory
                .select(new QResponseSuggestListDTO(
                        suggest.id,
                        author.nickname,
                        suggest.title,
                        suggest.createdAt,
                        suggest.isConfirmed
                ))
                .from(suggest)
                .join(suggest.fromUser, author)
                .where(SoftDeleteExpressions.isNotDeleted(suggest))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(suggest.count())
                .from(suggest)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponseSuggestListDTO> findAllByUser(Long userId, Pageable pageable) {
        QSuggest suggest = QSuggest.suggest;
        QUser author = new QUser("author");

        List<ResponseSuggestListDTO> contents = queryFactory
                .select(new QResponseSuggestListDTO(
                        suggest.id,
                        author.nickname,
                        suggest.title,
                        suggest.createdAt,
                        suggest.isConfirmed
                ))
                .from(suggest)
                .join(suggest.fromUser, author)
                .where(suggest.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(suggest))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(suggest.count())
                .from(suggest)
                .where(suggest.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(suggest))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QSuggest suggest = QSuggest.suggest;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, suggest.createdAt);
                        default ->
                            throw new SuggestException(SuggestExceptionType.SUGGEST_NOT_FOUND);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
