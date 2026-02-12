package kr.co.F1FS.app.domain.tag.infrastructure.adapter.tag;

import kr.co.F1FS.app.domain.tag.application.port.out.tag.TagJpaPort;
import kr.co.F1FS.app.domain.tag.application.service.tag.TagDomainService;
import kr.co.F1FS.app.domain.tag.domain.Tag;
import kr.co.F1FS.app.domain.tag.infrastructure.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagJpaAdapter implements TagJpaPort {
    private final TagRepository tagRepository;
    private final TagDomainService tagDomainService;

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag findByNameOrNewDomain(String name) {
        return tagRepository.findByName(name)
                .orElse(tagDomainService.createEntity(name));
    }
}
