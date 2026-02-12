package kr.co.F1FS.app.domain.tag.application.service.relation;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.tag.application.port.in.relation.CreateTagPostRelationUseCase;
import kr.co.F1FS.app.domain.tag.application.port.out.relation.TagPostRelationJpaPort;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTagPostRelationService implements CreateTagPostRelationUseCase {
    private final TagPostRelationJpaPort relationJpaPort;
    private final TagPostRelationDomainService relationDomainService;

    @Override
    public void save(Post post, Tag tag) {
        relationJpaPort.save(relationDomainService.createEntity(post, tag));
    }
}
