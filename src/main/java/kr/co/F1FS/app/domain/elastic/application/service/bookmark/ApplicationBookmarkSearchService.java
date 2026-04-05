package kr.co.F1FS.app.domain.elastic.application.service.bookmark;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.bookmark.BookmarkSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.BookmarkSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.BookmarkDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.BookmarkSearchRepository;
import kr.co.F1FS.app.global.presentation.dto.bookmark.ResponseBookmarkDocumentDTO;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkException;
import kr.co.F1FS.app.global.util.exception.bookmark.BookmarkExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationBookmarkSearchService implements BookmarkSearchUseCase {
    private final BookmarkSearchRepository bookmarkSearchRepository;
    private final BookmarkSearchRepoPort bookmarkSearchRepoPort;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final DocumentMapper documentMapper;

    @Override
    public Page<ResponseBookmarkDocumentDTO> getBookmarkList(int page, int size, String condition, String keyword) {
        if(keyword == null || keyword.length() < 2) return new PageImpl<>(List.of());
        Pageable pageable = PageRequest.of(page, size);
        NativeQuery query = setTitleQuery(keyword, pageable, condition);

        return getPageImpl(query, pageable, keyword);
    }

    public NativeQuery setTitleQuery(String keyword, Pageable pageable, String condition){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m.multiMatch(mm -> {
                                    mm.query(keyword)
                                            .fields("title.kor^2", "title.eng^2", "title.ngram")
                                            .operator(Operator.Or);
                                    return mm;
                                }))))
                .withPageable(pageable)
                .withSort(s -> {
                    switch (condition){
                        case "new":
                            s.field(f -> f.field("markingTime").order(SortOrder.Desc));
                            return s;
                        case "older":
                            s.field(f -> f.field("markingTime").order(SortOrder.Asc));
                            return s;
                        default:
                            throw new BookmarkException(BookmarkExceptionType.CONDITION_ERROR_BOOKMARK);
                    }
                })
                .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)))
                .build();

        return query;
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable, String keyword){
        SearchHits<BookmarkDocument> hits = elasticsearchTemplate.search(query, BookmarkDocument.class);
        List<ResponseBookmarkDocumentDTO> list = hits.stream()
                .map(hit -> documentMapper.toResponseBookmarkDocumentDTO(hit.getContent()))
                .toList();

        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(keyword.trim());

        return new PageImpl(list, pageable, hits.getTotalHits());
    }
}
