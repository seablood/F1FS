package kr.co.F1FS.app.domain.elastic.application.service.bookmark;

import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkSearchDocumentService {
    private final DocumentMapper documentMapper;

    public BookmarkDocument createEntity(Bookmark bookmark){
        return documentMapper.toBookmarkDocument(bookmark);
    }

    public void modify(BookmarkDocument bookmarkDocument, String title){
        bookmarkDocument.modify(title);
    }
}
