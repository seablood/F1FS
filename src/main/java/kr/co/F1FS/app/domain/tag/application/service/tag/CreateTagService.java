package kr.co.F1FS.app.domain.tag.application.service.tag;

import kr.co.F1FS.app.domain.elastic.application.port.in.suggest.redis.SaveSuggestKeywordSearchRedisUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.tag.CreateTagSearchUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.tag.application.port.in.relation.CreateTagPostRelationUseCase;
import kr.co.F1FS.app.domain.tag.application.port.in.tag.CreateTagUseCase;
import kr.co.F1FS.app.domain.tag.application.port.out.tag.TagJpaPort;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateTagService implements CreateTagUseCase {
    private final TagJpaPort tagJpaPort;
    private final CreateTagPostRelationUseCase createTagPostRelationUseCase;
    private final CreateTagSearchUseCase createTagSearchUseCase;
    private final SaveSuggestKeywordSearchRedisUseCase saveSuggestKeywordSearchRedisUseCase;

    @Override
    public void save(Post post, List<Tag> tags) {
        for (Tag tag : tags){
            if(tag.getId() == null){
                tag = tagJpaPort.save(tag);
                createTagSearchUseCase.save(tag);
            }

            saveSuggestKeyword(tag);
            createTagPostRelationUseCase.save(post, tag);
        }
    }

    public void saveSuggestKeyword(Tag tag){
        saveSuggestKeywordSearchRedisUseCase.increaseSearchCount(tag.getName());
    }
}
