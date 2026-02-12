package kr.co.F1FS.app.domain.tag.application.service.relation;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.tag.application.mapper.relation.TagPostRelationMapper;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.domain.tag.domain.TagPostRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagPostRelationDomainService {
    private final TagPostRelationMapper relationMapper;

    public TagPostRelation createEntity(Post post, Tag tag){
        return relationMapper.toTagPostRelation(post, tag);
    }
}
