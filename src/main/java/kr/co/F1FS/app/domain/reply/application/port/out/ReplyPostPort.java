package kr.co.F1FS.app.domain.reply.application.port.out;

import kr.co.F1FS.app.domain.post.domain.Post;

public interface ReplyPostPort {
    Post findByIdNotDTO(Long id);
}
