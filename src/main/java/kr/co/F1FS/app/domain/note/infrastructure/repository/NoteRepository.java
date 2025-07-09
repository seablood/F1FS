package kr.co.F1FS.app.domain.note.infrastructure.repository;

import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findAllByToUser(User toUser, Pageable pageable);
    Page<Note> findAllByFromUser(User fromUser, Pageable pageable);
}
