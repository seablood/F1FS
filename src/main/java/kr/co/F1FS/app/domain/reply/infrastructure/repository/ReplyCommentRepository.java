package kr.co.F1FS.app.domain.reply.infrastructure.repository;

import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {
}
