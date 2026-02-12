package kr.co.F1FS.app.domain.complain.note.infrastructure.repository;

import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteComplainRepository extends JpaRepository<NoteComplain, Long> {
    Page<NoteComplain> findAll(@NotNull Pageable pageable);
    Page<NoteComplain> findAllByFromUser(User fromUser, @NotNull Pageable pageable);
}
