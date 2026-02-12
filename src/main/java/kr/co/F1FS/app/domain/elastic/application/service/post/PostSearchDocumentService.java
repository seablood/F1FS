package kr.co.F1FS.app.domain.elastic.application.service.post;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSearchDocumentService {
    private final DocumentMapper documentMapper;

    public PostDocument createDocument(Post post, List<String> tags){
        return documentMapper.toPostDocument(post, tags);
    }

    public void modify(PostDocument document, Post post, List<String> tags){
        document.modify(post, tags);
    }

    public void increaseLikeNum(PostDocument document){
        document.increaseLikeNum();
    }

    public void decreaseLikeNum(PostDocument document){
        document.decreaseLikeNum();
    }
}
