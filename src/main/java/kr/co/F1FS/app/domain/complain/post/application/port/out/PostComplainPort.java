package kr.co.F1FS.app.domain.complain.post.application.port.out;

import kr.co.F1FS.app.domain.post.domain.Post;

public interface PostComplainPort {
    Post findByIdNotDTO(Long id);
}
