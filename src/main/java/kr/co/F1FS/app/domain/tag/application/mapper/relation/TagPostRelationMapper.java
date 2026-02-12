package kr.co.F1FS.app.domain.tag.application.mapper.relation;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.domain.tag.domain.TagPostRelation;
import org.springframework.stereotype.Component;

@Component
public class TagPostRelationMapper {
    public TagPostRelation toTagPostRelation(Post post, Tag tag){
        return TagPostRelation.builder()
                .post(post)
                .tag(tag)
                .build();
    }
}
