package kr.co.F1FS.app.domain.complain.reply.application.port.out;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyComplainJpaPort {
    ReplyComplain save(ReplyComplain replyComplain);
    Page<ReplyComplain> findAll(Pageable pageable);
    ReplyComplain findById(Long id);
    Page<ReplyComplain> findAllByFromUser(User user, Pageable pageable);
    void delete(ReplyComplain replyComplain);
}
