package kr.co.F1FS.app.domain.complain.reply.infrastructure.repository;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyComplainRepository extends JpaRepository<ReplyComplain, Long> {
    Page<ReplyComplain> findAll(Pageable pageable);
    Page<ReplyComplain> findAllByFromUser(User fromUser, Pageable pageable);
}
