package kr.co.F1FS.app.domain.elastic.application.service.post;

import kr.co.F1FS.app.domain.elastic.application.mapper.DocumentMapper;
import kr.co.F1FS.app.domain.elastic.domain.PostDocument;
import kr.co.F1FS.app.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSearchDocumentService {
    private final DocumentMapper documentMapper;

    public PostDocument createDocument(Post post){
        return documentMapper.toPostDocument(post);
    }

    public void modify(PostDocument document, Post post){
        document.modify(post);
    }

    public void increaseLikeNum(PostDocument document){
        document.increaseLikeNum();
    }

    public void decreaseLikeNum(PostDocument document){
        document.decreaseLikeNum();
    }
}
