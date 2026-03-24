package kr.co.F1FS.app.domain.complain.user.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.complain.user.domain.QUserComplain;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.infrastructure.repository.dsl.UserComplainDSLRepository;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.QResponseUserComplainListDTO;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.ResponseUserComplainListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserComplainDSLRepositoryImpl implements UserComplainDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserComplain> findById(Long id) {
        QUserComplain userComplain = QUserComplain.userComplain;
        QUser toUser = new QUser("toUser");
        QUser fromUser = new QUser("fromUser");

        UserComplain content = queryFactory
                .selectFrom(userComplain)
                .join(userComplain.toUser, toUser)
                .join(userComplain.fromUser, fromUser)
                .where(userComplain.id.eq(id))
                .fetchOne();

        return  Optional.ofNullable(content);
    }

    @Override
    public Page<ResponseUserComplainListDTO> findUserComplainList(Pageable pageable) {
        QUserComplain userComplain = QUserComplain.userComplain;
        QUser user = QUser.user;

        List<ResponseUserComplainListDTO> contents = queryFactory
                .select(new QResponseUserComplainListDTO(
                        userComplain.id,
                        user.nickname,
                        userComplain.description
                ))
                .from(userComplain)
                .join(userComplain.toUser, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(userComplain.count())
                .from(userComplain)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponseUserComplainListDTO> findAllByFromUser(Long userId, Pageable pageable) {
        QUserComplain userComplain = QUserComplain.userComplain;
        QUser user = QUser.user;

        List<ResponseUserComplainListDTO> contents = queryFactory
                .select(new QResponseUserComplainListDTO(
                        userComplain.id,
                        user.nickname,
                        userComplain.description
                ))
                .from(userComplain)
                .join(userComplain.toUser, user)
                .where(userComplain.fromUser.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(userComplain.count())
                .from(userComplain)
                .where(userComplain.fromUser.id.eq(userId))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponseUserComplainListDTO> findAllByToUser(Long userId, Pageable pageable) {
        QUserComplain userComplain = QUserComplain.userComplain;
        QUser user = QUser.user;

        List<ResponseUserComplainListDTO> contents = queryFactory
                .select(new QResponseUserComplainListDTO(
                        userComplain.id,
                        user.nickname,
                        userComplain.description
                ))
                .from(userComplain)
                .join(userComplain.toUser, user)
                .where(userComplain.toUser.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(userComplain.count())
                .from(userComplain)
                .where(userComplain.toUser.id.eq(userId))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QUserComplain userComplain = QUserComplain.userComplain;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, userComplain.createdAt);
                        default ->
                            throw new UserException(UserExceptionType.USER_COMPLAIN_NOT_FOUND);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
