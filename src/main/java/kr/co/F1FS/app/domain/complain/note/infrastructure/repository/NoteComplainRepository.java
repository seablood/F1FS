package kr.co.F1FS.app.domain.complain.note.infrastructure.repository;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteComplainRepository extends JpaRepository<NoteComplain, Long> {
}
