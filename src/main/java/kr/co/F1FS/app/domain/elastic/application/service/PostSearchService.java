package kr.co.F1FS.app.domain.elastic.application.service;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.application.port.in.PostSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostSearchRepository;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDocumentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostSearchService implements PostSearchUseCase {
    private final DocumentMapper documentMapper;
    private final PostSearchRepository postSearchRepository;
    private final PostSearchRepoPort postSearchRepoPort;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public void save(Post post){
        PostDocument postDocument = PostDocument.builder()
                .post(post).build();

        postSearchRepoPort.save(postDocument);
    }

    public void save(PostDocument postDocument){
        postSearchRepoPort.save(postDocument);
    }

    public PostDocument findById(Long id){
        return postSearchRepoPort.findById(id);
    }

    @Override
    public void modify(PostDocument document, Post post) {
        document.modify(post);
        postSearchRepoPort.save(document);
    }

    @Override
    public void delete(PostDocument document) {
        postSearchRepoPort.delete(document);
    }

    public void increaseLikeNum(PostDocument postDocument){
        postDocument.increaseLikeNum();
        postSearchRepoPort.save(postDocument);
    }

    public void decreaseLikeNum(PostDocument postDocument){
        postDocument.decreaseLikeNum();
        postSearchRepoPort.save(postDocument);
    }

    public NativeQuery setQueryTitle(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.match(m -> m
                                        .field("title.kor")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("title.eng")
                                        .query(keyword)
                                        .fuzziness("AUTO")))))
                .build();

        return query;
    }

    public NativeQuery setQueryContent(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.match(m -> m
                                        .field("content.kor")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("content.eng")
                                        .query(keyword)
                                        .fuzziness("AUTO")))))
                .build();

        return query;
    }

    public NativeQuery setQueryTitleOrContent(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.match(m -> m
                                        .field("title.kor")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("title.eng")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("content.kor")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("content.eng")
                                        .query(keyword)
                                        .fuzziness("AUTO")))))
                .build();

        return query;
    }

    public NativeQuery setQueryAuthor(String keyword){
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.match(m -> m
                                        .field("author.kor")
                                        .query(keyword)
                                        .fuzziness("AUTO")))
                                .should(s -> s.match(m -> m
                                        .field("author.eng")
                                        .query(keyword)
                                        .fuzziness("AUTO")))))
                .build();

        return query;
    }

    public Page<ResponsePostDocumentDTO> getPostList(int page, int size, String condition, String option, String keyword){
        Pageable pageable = switchCondition(page, size, condition);

        switch (option){
            case "title" :
                NativeQuery query = setQueryTitle(keyword);
                return getPageImpl(query, pageable);
            case "content" :
                NativeQuery query2 = setQueryContent(keyword);
                return getPageImpl(query2, pageable);
            case "titleOrContent" :
                NativeQuery query3 = setQueryTitleOrContent(keyword);
                return getPageImpl(query3, pageable);
            case "author" :
                NativeQuery query4 = setQueryAuthor(keyword);
                return getPageImpl(query4, pageable);
            default:
                throw new PostException(PostExceptionType.SEARCH_ERROR_POST);
        }
    }

    public Pageable switchCondition(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            case "like" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeNum"));
            default:
                throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
        }
    }

    public Comparator<PostDocument> getComparatorFromPageable(Pageable pageable){
        if(pageable.getSort().isEmpty()){
            return Comparator.comparing(PostDocument::getCreatedAt); // 기본값
        }

        Sort.Order order = pageable.getSort().iterator().next(); // 첫 번째 정렬 방식 사용
        String property = order.getProperty(); // 정렬 필드
        boolean ascending = order.getDirection().isAscending(); // 정렬 오름차순 여부

        return switch (property) {
            case "createdAt" -> ascending
                    ? Comparator.comparing(PostDocument::getCreatedAt)
                    : Comparator.comparing(PostDocument::getCreatedAt).reversed();
            case "likeNum" -> ascending
                    ? Comparator.comparing(PostDocument::getLikeNum)
                    : Comparator.comparing(PostDocument::getLikeNum).reversed();
            default -> Comparator.comparing(PostDocument::getCreatedAt); // fallback
        };
    }

    public PageImpl getPageImpl(NativeQuery query, Pageable pageable){
        SearchHits<PostDocument> hits = elasticsearchTemplate.search(query, PostDocument.class);
        List<PostDocument> list = hits.stream()
                .map(hit -> hit.getContent())
                .toList();

        Comparator<PostDocument> comparator = getComparatorFromPageable(pageable);
        List<ResponsePostDocumentDTO> sorted = list.stream()
                .sorted(comparator)
                .map(postDocument -> documentMapper.toResponsePostDocumentDTO(postDocument))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<ResponsePostDocumentDTO> paged = sorted.subList(start, end);

        return new PageImpl<>(paged, pageable, sorted.size());
    }
}
