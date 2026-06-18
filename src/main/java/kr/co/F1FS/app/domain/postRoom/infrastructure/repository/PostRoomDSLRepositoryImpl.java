package kr.co.F1FS.app.domain.postRoom.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.domain.QPostRoom;
import kr.co.F1FS.app.domain.postRoom.infrastructure.repository.dsl.PostRoomDSLRepository;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.QResponsePostRoomListDTO;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.SoftDeleteExpressions;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRoomDSLRepositoryImpl implements PostRoomDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostRoom> findById(Long id) {
        QPostRoom postRoom = QPostRoom.postRoom;
        QUser user = new QUser("masterUser");

        PostRoom content = queryFactory
                .selectFrom(postRoom)
                .join(postRoom.masterUser, user).fetchJoin()
                .where(postRoom.id.eq(id), SoftDeleteExpressions.isNotDeleted(postRoom))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponsePostRoomListDTO> findPostRoomList(Pageable pageable) {
        QPostRoom postRoom = QPostRoom.postRoom;
        QUser user = new QUser("masterUser");

        List<ResponsePostRoomListDTO> contents = queryFactory
                .select(new QResponsePostRoomListDTO(
                        postRoom.id,
                        user.nickname,
                        postRoom.roomTitle,
                        postRoom.description,
                        postRoom.isPublic,
                        postRoom.createdAt,
                        postRoom.updatedAt
                ))
                .from(postRoom)
                .join(postRoom.masterUser, user)
                .where(SoftDeleteExpressions.isNotDeleted(postRoom))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(postRoom.count())
                .from(postRoom)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponsePostRoomListDTO> findAllByUser(Long userId, Pageable pageable) {
        QPostRoom postRoom = QPostRoom.postRoom;
        QUser user = new QUser("masterUser");

        List<ResponsePostRoomListDTO> contents = queryFactory
                .select(new QResponsePostRoomListDTO(
                        postRoom.id,
                        user.nickname,
                        postRoom.roomTitle,
                        postRoom.description,
                        postRoom.isPublic,
                        postRoom.createdAt,
                        postRoom.updatedAt
                ))
                .from(postRoom)
                .join(postRoom.masterUser, user)
                .where(postRoom.masterUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(postRoom))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(postRoom.count())
                .from(postRoom)
                .where(postRoom.masterUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(postRoom))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QPostRoom postRoom = QPostRoom.postRoom;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, postRoom.createdAt);
                        default ->
                            throw new PostRoomException(PostRoomExceptionType.CONDITION_ERROR_POST_ROOM);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
