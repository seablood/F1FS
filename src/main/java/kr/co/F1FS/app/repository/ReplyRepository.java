package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Post;
import kr.co.F1FS.app.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPost(Post post);
}
