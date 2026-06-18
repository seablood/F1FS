package kr.co.F1FS.app.domain.form.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.domain.QPostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.infrastructure.repository.dsl.PostRoomDeleteFormDSLRepository;
import kr.co.F1FS.app.domain.form.presentation.dto.QResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.domain.postRoom.domain.QPostRoom;
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
public class PostRoomDeleteFormDSLRepositoryImpl implements PostRoomDeleteFormDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostRoomDeleteForm> findById(Long id) {
        QPostRoomDeleteForm postRoomDeleteForm = QPostRoomDeleteForm.postRoomDeleteForm;
        QUser user = new QUser("user");
        QPostRoom postRoom = new QPostRoom("postRoom");

        PostRoomDeleteForm content = queryFactory
                .selectFrom(postRoomDeleteForm)
                .join(postRoomDeleteForm.user, user).fetchJoin()
                .join(postRoomDeleteForm.postRoom, postRoom).fetchJoin()
                .where(postRoomDeleteForm.id.eq(id))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public List<PostRoomDeleteForm> findAllBeforeOneMonthAgo() {
        QPostRoomDeleteForm postRoomDeleteForm = QPostRoomDeleteForm.postRoomDeleteForm;
        Timestamp oneMonthAgo = Timestamp.valueOf(LocalDateTime.now().minusMonths(1));

        List<PostRoomDeleteForm> contents = queryFactory
                .selectFrom(postRoomDeleteForm)
                .where(postRoomDeleteForm.createdAt.before(oneMonthAgo))
                .fetch();

        return contents;
    }

    @Override
    public Page<ResponsePostRoomDeleteFormListDTO> findAllByUser(Long userId, Pageable pageable) {
        QPostRoomDeleteForm postRoomDeleteForm = QPostRoomDeleteForm.postRoomDeleteForm;
        QUser user = new QUser("user");

        List<ResponsePostRoomDeleteFormListDTO> contents = queryFactory
                .select(new QResponsePostRoomDeleteFormListDTO(
                        postRoomDeleteForm.id,
                        user.nickname,
                        postRoomDeleteForm.title,
                        postRoomDeleteForm.description,
                        postRoomDeleteForm.isConfirmed,
                        postRoomDeleteForm.createdAt
                ))
                .from(postRoomDeleteForm)
                .join(postRoomDeleteForm.user, user)
                .where(postRoomDeleteForm.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(postRoomDeleteForm.count())
                .from(postRoomDeleteForm)
                .where(postRoomDeleteForm.user.id.eq(userId))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponsePostRoomDeleteFormListDTO> findAllByIsConfirmed(boolean isConfirmed, Pageable pageable) {
        QPostRoomDeleteForm postRoomDeleteForm = QPostRoomDeleteForm.postRoomDeleteForm;
        QUser user = new QUser("user");
        List<ResponsePostRoomDeleteFormListDTO> contents;

        if(!isConfirmed){
            contents = queryFactory
                    .select(new QResponsePostRoomDeleteFormListDTO(
                            postRoomDeleteForm.id,
                            user.nickname,
                            postRoomDeleteForm.title,
                            postRoomDeleteForm.description,
                            postRoomDeleteForm.isConfirmed,
                            postRoomDeleteForm.createdAt
                    ))
                    .from(postRoomDeleteForm)
                    .join(postRoomDeleteForm.user, user)
                    .where(postRoomDeleteForm.isConfirmed.isFalse())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(getOrderSpecifiers(pageable))
                    .fetch();

            Long total = queryFactory
                    .select(postRoomDeleteForm.count())
                    .from(postRoomDeleteForm)
                    .where(postRoomDeleteForm.isConfirmed.isFalse())
                    .fetchOne();

            return new PageImpl<>(contents, pageable, total);
        }else {
            contents = queryFactory
                    .select(new QResponsePostRoomDeleteFormListDTO(
                            postRoomDeleteForm.id,
                            user.nickname,
                            postRoomDeleteForm.title,
                            postRoomDeleteForm.description,
                            postRoomDeleteForm.isConfirmed,
                            postRoomDeleteForm.createdAt
                    ))
                    .from(postRoomDeleteForm)
                    .join(postRoomDeleteForm.user, user)
                    .where(postRoomDeleteForm.isConfirmed.isTrue())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(getOrderSpecifiers(pageable))
                    .fetch();

            Long total = queryFactory
                    .select(postRoomDeleteForm.count())
                    .from(postRoomDeleteForm)
                    .where(postRoomDeleteForm.isConfirmed.isTrue())
                    .fetchOne();

            return new PageImpl<>(contents, pageable, total);
        }
    }

    @Override
    public boolean existsByPostRoom(Long roomId) {
        QPostRoomDeleteForm postRoomDeleteForm = QPostRoomDeleteForm.postRoomDeleteForm;

        return queryFactory
                .selectOne()
                .from(postRoomDeleteForm)
                .where(postRoomDeleteForm.postRoom.id.eq(roomId))
                .fetchFirst() != null;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QPostRoomDeleteForm postRoomDeleteForm = QPostRoomDeleteForm.postRoomDeleteForm;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return new OrderSpecifier<>(direction, postRoomDeleteForm.createdAt);
                })
                .toArray(OrderSpecifier[]::new);
    }
}
