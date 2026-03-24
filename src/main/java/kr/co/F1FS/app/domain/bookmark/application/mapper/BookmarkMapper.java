package kr.co.F1FS.app.domain.bookmark.application.mapper;

import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {
    public Bookmark toBookmark(Post post, User user){
        return Bookmark.builder()
                .post(post)
                .user(user)
                .build();
    }
}
