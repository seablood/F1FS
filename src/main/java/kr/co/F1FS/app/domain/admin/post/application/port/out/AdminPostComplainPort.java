package kr.co.F1FS.app.domain.admin.post.application.port.out;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPostComplainPort {
    Page<PostComplain> findAll(Pageable pageable);
}
