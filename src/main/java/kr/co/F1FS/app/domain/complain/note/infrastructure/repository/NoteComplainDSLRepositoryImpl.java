package kr.co.F1FS.app.domain.complain.note.infrastructure.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.note.domain.QNoteComplain;
import kr.co.F1FS.app.domain.complain.note.infrastructure.repository.dsl.NoteComplainDSLRepository;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.QResponseNoteComplainListDTO;
import kr.co.F1FS.app.domain.complain.note.presentation.dto.ResponseNoteComplainListDTO;
import kr.co.F1FS.app.domain.note.domain.QNote;
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
public class NoteComplainDSLRepositoryImpl implements NoteComplainDSLRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<NoteComplain> findById(Long id) {
        QNoteComplain noteComplain = QNoteComplain.noteComplain;
        QNote note = new QNote("note");
        QUser user = new QUser("user");

        NoteComplain content = queryFactory
                .selectFrom(noteComplain)
                .join(noteComplain.toNote, note).fetchJoin()
                .join(noteComplain.fromUser, user).fetchJoin()
                .where(noteComplain.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public Page<ResponseNoteComplainListDTO> findNoteComplainList(Pageable pageable) {
        QNoteComplain noteComplain = QNoteComplain.noteComplain;
        QNote note = QNote.note;

        List<ResponseNoteComplainListDTO> contents = queryFactory
                .select(new QResponseNoteComplainListDTO(
                        noteComplain.id,
                        note.id,
                        noteComplain.description
                ))
                .from(noteComplain)
                .join(noteComplain.toNote, note)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(noteComplain.count())
                .from(noteComplain)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public Page<ResponseNoteComplainListDTO> findAllByUser(Long userId, Pageable pageable) {
        QNoteComplain noteComplain = QNoteComplain.noteComplain;
        QNote note = QNote.note;

        List<ResponseNoteComplainListDTO> contents = queryFactory
                .select(new QResponseNoteComplainListDTO(
                        noteComplain.id,
                        note.id,
                        noteComplain.description
                ))
                .from(noteComplain)
                .join(noteComplain.toNote, note)
                .where(noteComplain.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(note))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable))
                .fetch();

        Long total = queryFactory
                .select(noteComplain.count())
                .from(noteComplain)
                .where(noteComplain.fromUser.id.eq(userId), SoftDeleteExpressions.isNotDeleted(note))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable){
        QNoteComplain noteComplain = QNoteComplain.noteComplain;

        return pageable.getSort().stream()
                .map(order -> {
                    Order direction = order.isAscending() ? Order.ASC : Order.DESC;

                    return switch (order.getProperty()){
                        case "createdAt" ->
                            new OrderSpecifier<>(direction, noteComplain.createdAt);
                        default ->
                            throw new NoteException(NoteExceptionType.NOTE_COMPLAIN_NOT_FOUND);
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }
}
