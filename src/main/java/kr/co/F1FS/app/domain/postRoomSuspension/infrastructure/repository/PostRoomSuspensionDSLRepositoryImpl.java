package kr.co.F1FS.app.domain.postRoomSuspension.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.postRoom.domain.QPostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.QPostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.infrastructure.repository.dsl.PostRoomSuspensionDSLRepository;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.QResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionException;
import kr.co.F1FS.app.global.util.exception.postRoomSuspension.PostRoomSuspensionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRoomSuspensionDSLRepositoryImpl implements PostRoomSuspensionDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<PostRoomSuspension> findById(Long id) {
        QPostRoomSuspension postRoomSuspension = QPostRoomSuspension.postRoomSuspension;
        QUser user = new QUser("suspendUser");
        QPostRoom postRoom = new QPostRoom("postRoom");

        PostRoomSuspension content = queryFactory
                .selectFrom(postRoomSuspension)
                .join(postRoomSuspension.suspendUser, user).fetchJoin()
                .join(postRoomSuspension.postRoom, postRoom).fetchJoin()
                .where(postRoomSuspension.id.eq(id))
                .distinct()
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponsePostRoomSuspensionListDTO> findPostRoomSuspensionListByPostRoom(Long roomId, Pageable pageable) {
        QPostRoomSuspension postRoomSuspension = QPostRoomSuspension.postRoomSuspension;
        QUser user = new QUser("suspendUser");

        List<ResponsePostRoomSuspensionListDTO> contents = queryFactory
                .select(new QResponsePostRoomSuspensionListDTO(
                        postRoomSuspension.id,
                        user.nickname,
                        postRoomSuspension.durationDays,
                        postRoomSuspension.suspendUntil
                ))
                .from(postRoomSuspension)
                .join(postRoomSuspension.suspendUser, user)
                .where(postRoomSuspension.postRoom.id.eq(roomId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(postRoomSuspension.count())
                .from(postRoomSuspension)
                .where(postRoomSuspension.postRoom.id.eq(roomId))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QPostRoomSuspension postRoomSuspension = QPostRoomSuspension.postRoomSuspension;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, postRoomSuspension.createdAt);
                        default ->
                            throw new PostRoomSuspensionException(PostRoomSuspensionExceptionType.CONDITION_ERROR_POST_ROOM_SUSPENSION);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
