package kr.co.F1FS.app.domain.repository.rdb.reply;

import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.model.rdb.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPost(Post post);
}
