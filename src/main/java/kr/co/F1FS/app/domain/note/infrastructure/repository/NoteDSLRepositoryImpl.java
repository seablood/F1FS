package kr.co.F1FS.app.domain.note.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.note.domain.QNote;
import kr.co.F1FS.app.domain.note.infrastructure.repository.dsl.NoteDSLRepository;
import kr.co.F1FS.app.domain.note.presentation.dto.QResponseNoteListDTO;
import kr.co.F1FS.app.domain.note.presentation.dto.ResponseNoteListDTO;
import kr.co.F1FS.app.domain.user.domain.QUser;
import kr.co.F1FS.app.global.util.SoftDeleteExpressions;
import kr.co.F1FS.app.global.util.exception.note.NoteException;
import kr.co.F1FS.app.global.util.exception.note.NoteExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoteDSLRepositoryImpl implements NoteDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Note> findById(Long id) {
        QNote note = QNote.note;
        QUser toUser = new QUser("toUser");
        QUser fromUser = new QUser("fromUser");

        Note content = queryFactory
                .selectFrom(note)
                .join(note.toUser, toUser)
                .join(note.fromUser, fromUser)
                .where(note.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponseNoteListDTO> findAllByToUser(Long userId, Pageable pageable) {
        QNote note = QNote.note;
        QUser fromUser = new QUser("fromUser");

        List<ResponseNoteListDTO> contents = queryFactory
                .select(new QResponseNoteListDTO(
                        note.id,
                        fromUser.nickname,
                        note.createdAt,
                        note.isRead
                ))
                .from(note)
                .join(note.fromUser, fromUser)
                .where(note.toUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(note))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(note.count())
                .from(note)
                .where(note.toUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(note))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponseNoteListDTO> findAllByFromUser(Long userId, Pageable pageable) {
        QNote note = QNote.note;
        QUser toUser = new QUser("toUser");

        List<ResponseNoteListDTO> contents = queryFactory
                .select(new QResponseNoteListDTO(
                        note.id,
                        toUser.nickname,
                        note.createdAt,
                        note.isRead
                ))
                .from(note)
                .join(note.toUser, toUser)
                .where(note.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(note))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(note.count())
                .from(note)
                .where(note.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(note))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QNote note = QNote.note;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, note.createdAt);
                        default ->
                            throw new NoteException(NoteExceptionType.NOTE_NOT_FOUND);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
