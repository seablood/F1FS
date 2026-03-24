package kr.co.F1FS.app.domain.bookmark.application.service;

import kr.co.F1FS.app.domain.bookmark.application.port.in.BookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.application.port.in.CreateBookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.application.port.in.DeleteBookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.application.port.in.QueryBookmarkUseCase;
import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.bookmark.presentation.dto.ResponseBookmarkListDTO;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkException;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationBookmarkService implements BookmarkUseCase {
    private final CreateBookmarkUseCase createBookmarkUseCase;
    private final DeleteBookmarkUseCase deleteBookmarkUseCase;
    private final QueryBookmarkUseCase queryBookmarkUseCase;
    private final QueryPostUseCase queryPostUseCase;

    @Override
    @Transactional
    public void save(Long postId, User user) {
        Post post = queryPostUseCase.findPostById(postId);

        createBookmarkUseCase.save(post, user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseBookmarkListDTO> getBookmarkAllList(int page, int size, String condition, User user) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryBookmarkUseCase.findBookmarkListForDTO(user.getId(), pageable);
    }

    @Override
    @Transactional
    public void delete(Long postId, User user) {
        Post post = queryPostUseCase.findById(postId);
        Bookmark bookmark = queryBookmarkUseCase.findByPostAndUser(post, user);

        deleteBookmarkUseCase.delete(bookmark);
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "markingTime"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "markingTime"));
            default:
                throw new BookmarkException(BookmarkExceptionType.CONDITION_ERROR_BOOKMARK);
        }
    }
}
