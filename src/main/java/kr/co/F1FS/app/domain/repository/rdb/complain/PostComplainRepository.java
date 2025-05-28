package kr.co.F1FS.app.domain.repository.rdb.complain;

import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.model.rdb.PostComplain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostComplainRepository extends JpaRepository<PostComplain, Long> {
    Page<PostComplain> findAll(Pageable pageable);
}
