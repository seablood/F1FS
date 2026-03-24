package kr.co.F1FS.app.domain.reply.infrastructure.repository;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
