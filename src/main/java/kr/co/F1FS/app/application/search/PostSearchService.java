package kr.co.F1FS.app.application.search;

import kr.co.F1FS.app.domain.model.elastic.PostDocument;
import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.repository.elastic.PostSearchRepository;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import kr.co.F1FS.app.presentation.post.dto.ResponsePostDocumentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostSearchService {
    private final PostSearchRepository postSearchRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public void save(Post post){
        PostDocument postDocument = PostDocument.builder()
                .post(post).build();

        postSearchRepository.save(postDocument);
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
                SearchHits<PostDocument> hits = elasticsearchTemplate.search(query, PostDocument.class);
                List<ResponsePostDocumentDTO> list = hits.stream()
                        .map(hit -> hit.getContent())
                        .map(postDocument -> ResponsePostDocumentDTO.toDto(postDocument))
                        .toList();
                return new PageImpl<>(list, pageable, hits.getTotalHits());
            case "content" :
                NativeQuery query2 = setQueryContent(keyword);
                SearchHits<PostDocument> hits2 = elasticsearchTemplate.search(query2, PostDocument.class);
                List<ResponsePostDocumentDTO> list2 = hits2.stream()
                        .map(hit -> hit.getContent())
                        .map(postDocument -> ResponsePostDocumentDTO.toDto(postDocument))
                        .toList();
                return new PageImpl<>(list2, pageable, hits2.getTotalHits());
            case "titleOrContent" :
                NativeQuery query3 = setQueryTitleOrContent(keyword);
                SearchHits<PostDocument> hits3 = elasticsearchTemplate.search(query3, PostDocument.class);
                List<ResponsePostDocumentDTO> list3 = hits3.stream()
                        .map(hit -> hit.getContent())
                        .map(postDocument -> ResponsePostDocumentDTO.toDto(postDocument))
                        .toList();
                return new PageImpl<>(list3, pageable, hits3.getTotalHits());
            case "author" :
                NativeQuery query4 = setQueryAuthor(keyword);
                SearchHits<PostDocument> hits4 = elasticsearchTemplate.search(query4, PostDocument.class);
                List<ResponsePostDocumentDTO> list4 = hits4.stream()
                        .map(hit -> hit.getContent())
                        .map(postDocument -> ResponsePostDocumentDTO.toDto(postDocument))
                        .toList();
                return new PageImpl<>(list4, pageable, hits4.getTotalHits());
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
}
