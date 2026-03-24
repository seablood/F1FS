package kr.co.F1FS.app.domain.complain.reply.infrastructure.repository;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyComplainRepository extends JpaRepository<ReplyComplain, Long> {
}
