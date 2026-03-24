package kr.co.F1FS.app.domain.bookmark.application.service;

import kr.co.F1FS.app.domain.bookmark.application.mapper.BookmarkMapper;
import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkDomainService {
    private final BookmarkMapper bookmarkMapper;

    public Bookmark createEntity(Post post, User user){
        return bookmarkMapper.toBookmark(post, user);
    }
}
