package kr.co.F1FS.app.domain.note.infrastructure.repository;

import kr.co.F1FS.app.domain.note.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
