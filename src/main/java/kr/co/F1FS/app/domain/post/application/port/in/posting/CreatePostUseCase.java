package kr.co.F1FS.app.domain.post.application.port.in.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.CreatePostDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreatePostUseCase {
    Post save(CreatePostDTO postDTO, User author);
}
