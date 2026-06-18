package kr.co.F1FS.app.domain.form.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.domain.QPostRoomForm;
import kr.co.F1FS.app.domain.form.infrastructure.repository.dsl.PostRoomFormDSLRepository;
import kr.co.F1FS.app.domain.form.presentation.dto.QResponsePostRoomFormListDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRoomFormDSLRepositoryImpl implements PostRoomFormDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostRoomForm> findById(Long id) {
        QPostRoomForm postRoomForm = QPostRoomForm.postRoomForm;
        QUser user = new QUser("user");

        PostRoomForm content = queryFactory
                .selectFrom(postRoomForm)
                .join(postRoomForm.user, user).fetchJoin()
                .where(postRoomForm.id.eq(id))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public List<PostRoomForm> findAllBeforeOneMonthAgo() {
        QPostRoomForm postRoomForm = QPostRoomForm.postRoomForm;
        Timestamp oneMonthAgo = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));

        List<PostRoomForm> contents = queryFactory
                .selectFrom(postRoomForm)
                .where(postRoomForm.createdAt.before(oneMonthAgo))
                .fetch();

        return contents;
    }

    @Override
    public Page<ResponsePostRoomFormListDTO> findAllByUser(Long userId, Pageable pageable) {
        QPostRoomForm postRoomForm = QPostRoomForm.postRoomForm;
        QUser user = new QUser("user");

        List<ResponsePostRoomFormListDTO> contents = queryFactory
                .select(new QResponsePostRoomFormListDTO(
                        postRoomForm.id,
                        user.nickname,
                        postRoomForm.roomTitle,
                        postRoomForm.createdAt,
                        postRoomForm.isConfirmed
                ))
                .from(postRoomForm)
                .join(postRoomForm.user, user)
                .where(postRoomForm.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(postRoomForm.count())
                .from(postRoomForm)
                .where(postRoomForm.user.id.eq(userId))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponsePostRoomFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable) {
        QPostRoomForm postRoomForm = QPostRoomForm.postRoomForm;
        QUser user = new QUser("user");
        List<ResponsePostRoomFormListDTO> contents;

        if(!isConfirmed){
            contents = queryFactory
                    .select(new QResponsePostRoomFormListDTO(
                            postRoomForm.id,
                            user.nickname,
                            postRoomForm.roomTitle,
                            postRoomForm.createdAt,
                            postRoomForm.isConfirmed
                    ))
                    .from(postRoomForm)
                    .join(postRoomForm.user, user)
                    .where(postRoomForm.isConfirmed.isFalse())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(getOrderSpecifiers(pageable))
                    .fetch();

            Long total = queryFactory
                    .select(postRoomForm.count())
                    .from(postRoomForm)
                    .where(postRoomForm.isConfirmed.isFalse())
                    .fetchOne();

            return new PageImpl<>(contents, pageable, total);
        }else {
            contents = queryFactory
                    .select(new QResponsePostRoomFormListDTO(
                            postRoomForm.id,
                            user.nickname,
                            postRoomForm.roomTitle,
                            postRoomForm.createdAt,
                            postRoomForm.isConfirmed
                    ))
                    .from(postRoomForm)
                    .join(postRoomForm.user, user)
                    .where(postRoomForm.isConfirmed.isTrue())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(getOrderSpecifiers(pageable))
                    .fetch();

            Long total = queryFactory
                    .select(postRoomForm.count())
                    .from(postRoomForm)
                    .where(postRoomForm.isConfirmed.isTrue())
                    .fetchOne();

            return new PageImpl<>(contents, pageable, total);
        }
    }

    @Override
    public boolean existsByUser(Long userId) {
        QPostRoomForm postRoomForm = QPostRoomForm.postRoomForm;

        return queryFactory
                .selectOne()
                .from(postRoomForm)
                .where(postRoomForm.user.id.eq(userId))
                .fetchFirst() != null;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QPostRoomForm postRoomForm = QPostRoomForm.postRoomForm;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return new OrderSpecifier<>(direction, postRoomForm.createdAt);
                })
                .toArray(OrderSpecifier[]::new);
    }
}
