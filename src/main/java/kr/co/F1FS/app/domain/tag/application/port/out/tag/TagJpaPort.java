package kr.co.F1FS.app.domain.tag.application.port.out.tag;

import kr.co.F1FS.app.domain.tag.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagJpaPort {
    Tag save(Tag tag);
    Page<Tag> findAll(Pageable pageable);
    Tag findByNameOrNewDomain(String name);
}
