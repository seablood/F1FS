package kr.co.F1FS.app.domain.form.infrastructure.repository;

import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRoomFormRepository extends JpaRepository<PostRoomForm, Long> {
}
