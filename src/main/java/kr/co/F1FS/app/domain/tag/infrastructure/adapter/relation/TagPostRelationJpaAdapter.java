package kr.co.F1FS.app.domain.tag.infrastructure.adapter.relation;

import kr.co.F1FS.app.domain.tag.application.port.out.relation.TagPostRelationJpaPort;
import kr.co.F1FS.app.domain.tag.domain.TagPostRelation;
import kr.co.F1FS.app.domain.tag.infrastructure.repository.TagPostRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagPostRelationJpaAdapter implements TagPostRelationJpaPort {
    private final TagPostRelationRepository relationRepository;

    @Override
    public TagPostRelation save(TagPostRelation relation) {
        return relationRepository.save(relation);
    }
}
